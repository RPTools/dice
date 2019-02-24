package net.rptools.maptool.dice.expressiontree;

import net.rptools.maptool.dice.result.DiceExprResult;
import net.rptools.maptool.dice.symbols.DiceExpressionSymbolTable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class InstructionDiceExpressionNode implements DiceExpressionNode {

    private final String instructionName;

    private final List<String> arguments;

    public InstructionDiceExpressionNode(String name, Collection<String> args) {
        instructionName = name;
        arguments = List.copyOf(args);
    }

    @Override
    public DiceExprResult evaluate(DiceExpressionSymbolTable symbolTable) throws UnsupportedOperationException {
        return null;
    }

    @Override
    public DiceExprResult getExprResult() {
        return null;
    }

    @Override
    public Collection<DiceExpressionNode> getChildren() {
        return Collections.emptyList();
    }


    String getInstructionName() {
        return instructionName;
    }

    List<String> getArguments() {
        return arguments;
    }
}
