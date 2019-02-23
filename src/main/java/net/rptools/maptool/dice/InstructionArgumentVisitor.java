package net.rptools.maptool.dice;


import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InstructionArgumentVisitor extends DiceExprBaseVisitor<List<String>> {

    @Override
    public List<String> visitInstructionArgumentList(DiceExprParser.InstructionArgumentListContext ctx) {
        return ctx.instructionArgument().stream().map(arg -> arg.accept(this)).flatMap(Collection::stream).collect(Collectors.toList());
    }

    @Override
    public List<String> visitInstructionArgument(DiceExprParser.InstructionArgumentContext ctx) {
        return Collections.singletonList(ctx.WORD().getText());
    }

    @Override
    public List<String> visitDiceArgumentList(DiceExprParser.DiceArgumentListContext ctx) {
        return ctx.diceArgument().stream().map(arg -> arg.accept(this)).flatMap(Collection::stream).collect(Collectors.toList());
    }
}
