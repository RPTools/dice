package net.rptools.maptool.dice.result;

import net.rptools.maptool.dice.DiceArgumentVisitor;
import net.rptools.maptool.dice.DiceExprBaseVisitor;
import net.rptools.maptool.dice.DiceExprParser;
import net.rptools.maptool.dice.InstructionArgumentVisitor;
import net.rptools.maptool.dice.roller.DiceRollerArgument;
import net.rptools.maptool.dice.symbols.DiceEvalScope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiceRollVisitor extends DiceExprBaseVisitor<DiceExpressionNode> {

    private final List<DiceExpressionNode> roots = new ArrayList<>();


    private String stripStringQuotes(String str) {
        return str.substring(1, str.length() -1);
    }


    public List<DiceExpressionNode> getExpressionTrees() {
        return Collections.unmodifiableList(roots);
    }

    @Override
    public DiceExpressionNode visitDiceRolls(DiceExprParser.DiceRollsContext ctx) {
        ctx.diceExpr().stream().forEach(de -> roots.add(visit(de)));
        return roots.get(roots.size() - 1);
    }

    @Override
    public DiceExpressionNode visitDoubleValue(DiceExprParser.DoubleValueContext ctx) {
        return new ValueDiceExpressionNode(Double.parseDouble(ctx.DOUBLE().getText()));
    }

    @Override
    public DiceExpressionNode visitIntegerValue(DiceExprParser.IntegerValueContext ctx) {
        return new ValueDiceExpressionNode(Integer.parseInt(ctx.INTEGER().getText()));
    }

    @Override
    public DiceExpressionNode visitString(DiceExprParser.StringContext ctx) {
        return new ValueDiceExpressionNode(stripStringQuotes(ctx.getText()));
    }

    @Override
    public DiceExpressionNode visitParenGroup(DiceExprParser.ParenGroupContext ctx) {
        return new GroupDiceExpressionNode(
                GroupDiceExpressionNode.GroupingType.PAREN,
                visit(ctx.val)
        );

    }

    @Override
    public DiceExpressionNode visitBraceGroup(DiceExprParser.BraceGroupContext ctx) {
        return new GroupDiceExpressionNode(
                GroupDiceExpressionNode.GroupingType.BRACE,
                visit(ctx.val)
        );

    }

    @Override
    public DiceExpressionNode visitBinaryExpr(DiceExprParser.BinaryExprContext ctx) {
        return new BinaryDiceExpressionNode(
                ctx.op.getText(),
                visit(ctx.left),
                visit(ctx.right)
        );
    }

    @Override
    public DiceExpressionNode visitUnaryExpr(DiceExprParser.UnaryExprContext ctx) {
        return new UnaryDiceExpressionNode(ctx.op.getText(), visit(ctx.right));
    }

    @Override
    public DiceExpressionNode visitAssignment(DiceExprParser.AssignmentContext ctx) {
        String variableScopePrefix = ctx.variable().getText().substring(0,1);
        String variableName = ctx.variable().getText().substring(1);

        return new AssignmentDiceExpressionNode(
                variableName, DiceEvalScope.getScopeForPrefix(variableScopePrefix), visit(ctx.right)
        );
    }

    @Override
    public DiceExpressionNode visitLocalVariable(DiceExprParser.LocalVariableContext ctx) {
        return new ResolveSymbolDiceExpressionNode(
                ctx.LOCAL_VARIABLE().getText().substring(1),
                DiceEvalScope.LOCAL
        );
    }

    @Override
    public DiceExpressionNode visitGlobalVariable(DiceExprParser.GlobalVariableContext ctx) {
        return new ResolveSymbolDiceExpressionNode(
                ctx.GLOBAL_VARIABLE().getText().substring(1),
                DiceEvalScope.GLOBAL
        );
    }

    @Override
    public DiceExpressionNode visitPropertyVariable(DiceExprParser.PropertyVariableContext ctx) {
        return new ResolveSymbolDiceExpressionNode(
                ctx.PROPERTY_VARIABLE().getText().substring(1),
                DiceEvalScope.PROPERTY
        );
    }

    @Override
    public DiceExpressionNode visitDice(DiceExprParser.DiceContext ctx) {
        DiceExpressionNode numDice;
        if (ctx.numDice() == null) {
            numDice = new ValueDiceExpressionNode(1);
        } else {
            numDice = visit(ctx.numDice());
        }

        final DiceExpressionNode sides = visit(ctx.diceSides());
        final String name = ctx.diceName.getText();


        List<DiceRollerArgument> argList;
        if (ctx.diceArgumentList() != null) {
            final var diceArgVisitor = new DiceArgumentVisitor();
            argList = diceArgVisitor.visit(ctx.diceArgumentList());
        } else {
            argList = Collections.emptyList();
        }

        return new DiceRollDiceExpressionNode(name, numDice, sides, argList, ctx.getText());
    }

    @Override
    public DiceExpressionNode visitNumDice(DiceExprParser.NumDiceContext ctx) {
        if (ctx.integerValue() != null) {
            return new ValueDiceExpressionNode(Integer.parseInt(ctx.getText()));
        } else {
            return visit(ctx.group());
        }
    }

    @Override
    public DiceExpressionNode visitDiceSides(DiceExprParser.DiceSidesContext ctx) {
        if (ctx.integerValue() != null) {
            return new ValueDiceExpressionNode(Integer.parseInt(ctx.getText()));
        } else {
            return visit(ctx.group());
        }
    }

    @Override
    public DiceExpressionNode visitInstruction(DiceExprParser.InstructionContext ctx) {
        String instrName = ctx.instructionName.getText();


        List<String> argList;
        if (ctx.instructionArgumentList() != null) {
            final var instructionArgVisitor = new InstructionArgumentVisitor();
            argList = instructionArgVisitor.visit(ctx.instructionArgumentList());
        } else {
            argList = Collections.emptyList();
        }

        return new InstructionDiceExpressionNode(instrName, argList);
    }
}
