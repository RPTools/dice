package net.rptools.maptool.dice;

import net.rptools.maptool.dice.roller.DiceRollers;
import net.rptools.maptool.dice.roller.DiceRollerArgument;
import net.rptools.maptool.dice.symbols.DiceExpressionSymbolTable;
import net.rptools.maptool.dice.symbols.DiceEvalScope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiceRollVisitor extends DiceExprBaseVisitor<DiceExprResultOld> {

    private final DiceExpressionSymbolTable symbolTable;

    private final List<DiceExprResultOld> results = new ArrayList<>();


    public DiceRollVisitor(DiceExpressionSymbolTable stable) {
        symbolTable = stable;
    }

    public DiceExpressionSymbolTable getSymbolTable() {
        return symbolTable;
    }

    private String stripStringQuotes(String str) {
        return str.substring(1, str.length() -1);
    }


    public List<DiceExprResultOld> getResults() {
        return Collections.unmodifiableList(results);
    }

    @Override
    public DiceExprResultOld visitDiceRolls(DiceExprParser.DiceRollsContext ctx) {
        ctx.diceExpr().stream().forEach(de -> results.add(visit(de)));
        return results.get(results.size() - 1);
    }

    @Override
    public DiceExprResultOld visitDoubleValue(DiceExprParser.DoubleValueContext ctx) {
        return new DiceExprResultOld(Double.parseDouble(ctx.DOUBLE().getText()));
    }

    @Override
    public DiceExprResultOld visitIntegerValue(DiceExprParser.IntegerValueContext ctx) {
        return new DiceExprResultOld(Integer.parseInt(ctx.INTEGER().getText()));
    }

    @Override
    public DiceExprResultOld visitString(DiceExprParser.StringContext ctx) {
        return new DiceExprResultOld(stripStringQuotes(ctx.getText()));
    }

    /*@Override
    public DiceExprResultOld visitParenExpr(DiceExprParser.ParenExprContext ctx) {
        return visit(ctx.val);
    }*/

    @Override
    public DiceExprResultOld visitGroup(DiceExprParser.GroupContext ctx) {
        return visit(ctx.val);
    }

    @Override
    public DiceExprResultOld visitBinaryExpr(DiceExprParser.BinaryExprContext ctx) {
        DiceExprResultOld left = visit(ctx.left);
        DiceExprResultOld right = visit(ctx.right);

        DiceExprResultOld result = DiceExprResultOld.UNDEFINED;

        switch (ctx.op.getText()) {
            case "+":
                result = DiceExprResultOld.add(left, right);
                break;
            case "-":
                result = DiceExprResultOld.subtract(left, right);
                break;
            case "*":
                result = DiceExprResultOld.multiply(left, right);
                break;
            case "/":
                result = DiceExprResultOld.divide(left, right);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Binary expression " + ctx.op.getText()); // TODO
        }
        return result;
    }

    @Override
    public DiceExprResultOld visitUnaryExpr(DiceExprParser.UnaryExprContext ctx) {
        DiceExprResultOld right = visit(ctx.right);

        DiceExprResultOld result = DiceExprResultOld.UNDEFINED;
        if (ctx.op.getText().equals("-")) {
            result = DiceExprResultOld.negate(right);
        }

        return result;
    }

    @Override
    public DiceExprResultOld visitAssignment(DiceExprParser.AssignmentContext ctx) {
        String variableType = ctx.variable().getText().substring(0,1);
        String variableName = ctx.variable().getText().substring(1);
        DiceExprResultOld right = visit(ctx.right);
        DiceEvalScope scope = DiceEvalScope.LOCAL;
        switch (variableType) {
            case "$":
                scope = DiceEvalScope.LOCAL;
                break;
            case "%":
                scope = DiceEvalScope.GLOBAL;
                break;
            case "@":
                scope = DiceEvalScope.PROPERTY;
        }
        symbolTable.setVariableValue(scope, variableName, right);
        return right;
    }

    @Override
    public DiceExprResultOld visitLocalVariable(DiceExprParser.LocalVariableContext ctx) {
        String variableName = ctx.LOCAL_VARIABLE().getText().substring(1);
        DiceExprResultOld val = symbolTable.getVariableValue(DiceEvalScope.LOCAL, variableName);
        return val;
    }

    @Override
    public DiceExprResultOld visitGlobalVariable(DiceExprParser.GlobalVariableContext ctx) {
        String variableName = ctx.GLOBAL_VARIABLE().getText().substring(1);
        DiceExprResultOld val = symbolTable.getVariableValue(DiceEvalScope.GLOBAL, variableName);
        return val;
    }

    @Override
    public DiceExprResultOld visitPropertyVariable(DiceExprParser.PropertyVariableContext ctx) {
        String variableName = ctx.PROPERTY_VARIABLE().getText().substring(1);
        DiceExprResultOld val = symbolTable.getVariableValue(DiceEvalScope.PROPERTY, variableName);
        return val;
    }

    @Override
    public DiceExprResultOld visitDice(DiceExprParser.DiceContext ctx) {
        DiceExprResultOld numDice = DiceExprResultOld.ONE;
        if (ctx.numDice() != null) {
            numDice = visit(ctx.numDice());
        }
        final DiceExprResultOld sides = visit(ctx.diceSides());
        final String name = ctx.diceName.getText();


        if (ctx.diceArgumentList() != null) {
            final var diceArgVisitor = new DiceArgumentVisitor();
            final List<DiceRollerArgument> visit = diceArgVisitor.visit(ctx.diceArgumentList());
        }

        DiceExprResultOld res = DiceRollers.getInstance().getDiceRoller(name).roll(name, numDice.getIntResult(), sides.getIntResult());
        return res;
    }

    @Override
    public DiceExprResultOld visitNumDice(DiceExprParser.NumDiceContext ctx) {
        if (ctx.integerValue() != null) {
            return new DiceExprResultOld(Integer.parseInt(ctx.getText()));
        } else {
            return new DiceExprResultOld(visit(ctx.group()).getIntResult());
        }
    }

    @Override
    public DiceExprResultOld visitDiceSides(DiceExprParser.DiceSidesContext ctx) {
        if (ctx.integerValue() != null) {
            return new DiceExprResultOld(Integer.parseInt(ctx.getText()));
        } else {
            return new DiceExprResultOld(visit(ctx.group()).getIntResult());
        }
    }
}
