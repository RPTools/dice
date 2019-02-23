package net.rptools.maptool.dice.result;

import net.rptools.maptool.dice.DiceArgumentVisitor;
import net.rptools.maptool.dice.DiceExprBaseVisitor;
import net.rptools.maptool.dice.DiceExprParser;
import net.rptools.maptool.dice.roller.DiceRollerArgument;
import net.rptools.maptool.dice.symbols.DiceEvalScope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiceRollVisitor extends DiceExprBaseVisitor<DiceResultNode> {

    private final List<DiceResultNode> results = new ArrayList<>();


    private String stripStringQuotes(String str) {
        return str.substring(1, str.length() -1);
    }


    public List<DiceResultNode> getResults() {
        return Collections.unmodifiableList(results);
    }

    @Override
    public DiceResultNode visitDiceRolls(DiceExprParser.DiceRollsContext ctx) {
        ctx.diceExpr().stream().forEach(de -> results.add(visit(de)));
        return results.get(results.size() - 1);
    }

    @Override
    public DiceResultNode visitDoubleValue(DiceExprParser.DoubleValueContext ctx) {
        return new ValueDiceResultOpNode(Double.parseDouble(ctx.DOUBLE().getText()));
    }

    @Override
    public DiceResultNode visitIntegerValue(DiceExprParser.IntegerValueContext ctx) {
        return new ValueDiceResultOpNode(Integer.parseInt(ctx.INTEGER().getText()));
    }

    @Override
    public DiceResultNode visitString(DiceExprParser.StringContext ctx) {
        return new ValueDiceResultOpNode(stripStringQuotes(ctx.getText()));
    }

    /*@Override
    public DiceExprResult visitParenExpr(DiceExprParser.ParenExprContext ctx) {
        return visit(ctx.val);
    }*/

    @Override
    public DiceResultNode visitParenGroup(DiceExprParser.ParenGroupContext ctx) {
        return new GroupDiceResultOpNode(
                GroupDiceResultOpNode.GroupingType.PAREN,
                visit(ctx.val)
        );

    }

    @Override
    public DiceResultNode visitBraceGroup(DiceExprParser.BraceGroupContext ctx) {
        return new GroupDiceResultOpNode(
                GroupDiceResultOpNode.GroupingType.BRACE,
                visit(ctx.val)
        );

    }

    @Override
    public DiceResultNode visitBinaryExpr(DiceExprParser.BinaryExprContext ctx) {
        return new BinaryDiceResultOpNode(
                ctx.op.getText(),
                visit(ctx.left),
                visit(ctx.right)
        );
    }

    @Override
    public DiceResultNode visitUnaryExpr(DiceExprParser.UnaryExprContext ctx) {
        return new UnaryDiceResultOpNode(ctx.op.getText(), visit(ctx.right));
    }

    @Override
    public DiceResultNode visitAssignment(DiceExprParser.AssignmentContext ctx) {
        String variableScopePrefix = ctx.variable().getText().substring(0,1);
        String variableName = ctx.variable().getText().substring(1);

        return new AssignmentDiceResultOpNode(
                variableName, DiceEvalScope.getScopeForPrefix(variableScopePrefix), visit(ctx.right)
        );
    }

    @Override
    public DiceResultNode visitLocalVariable(DiceExprParser.LocalVariableContext ctx) {
        return new ResolveSymbolDiceResultOpNode(
                ctx.LOCAL_VARIABLE().getText().substring(1),
                DiceEvalScope.LOCAL
        );
    }

    @Override
    public DiceResultNode visitGlobalVariable(DiceExprParser.GlobalVariableContext ctx) {
        return new ResolveSymbolDiceResultOpNode(
                ctx.GLOBAL_VARIABLE().getText().substring(1),
                DiceEvalScope.GLOBAL
        );
    }

    @Override
    public DiceResultNode visitPropertyVariable(DiceExprParser.PropertyVariableContext ctx) {
        return new ResolveSymbolDiceResultOpNode(
                ctx.PROPERTY_VARIABLE().getText().substring(1),
                DiceEvalScope.PROPERTY
        );
    }

    @Override
    public DiceResultNode visitDice(DiceExprParser.DiceContext ctx) {
        DiceResultNode numDice;
        if (ctx.numDice() == null) {
            numDice = new ValueDiceResultOpNode(1);
        } else {
            numDice = visit(ctx.numDice());
        }

        final DiceResultNode sides = visit(ctx.diceSides());
        final String name = ctx.diceName.getText();


        List<DiceRollerArgument> argList;
        if (ctx.diceArgumentList() != null) {
            final var diceArgVisitor = new DiceArgumentVisitor();
            argList = diceArgVisitor.visit(ctx.diceArgumentList());
        } else {
            argList = Collections.EMPTY_LIST;
        }

        return new DiceDiceResultOpNode(name, numDice, sides, argList, ctx.getText());
    }

    @Override
    public DiceResultNode visitNumDice(DiceExprParser.NumDiceContext ctx) {
        if (ctx.integerValue() != null) {
            return new ValueDiceResultOpNode(Integer.parseInt(ctx.getText()));
        } else {
            return visit(ctx.group());
        }
    }

    @Override
    public DiceResultNode visitDiceSides(DiceExprParser.DiceSidesContext ctx) {
        if (ctx.integerValue() != null) {
            return new ValueDiceResultOpNode(Integer.parseInt(ctx.getText()));
        } else {
            return visit(ctx.group());
        }
    }
}
