package net.rptools.maptool.dice.result.tree;

import net.rptools.maptool.dice.result.DiceExprResult;
import net.rptools.maptool.dice.result.tree.DiceExpressionNode;
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
    public String getFormattedText() {
        return "";
    }

    @Override
    public Collection<DiceExpressionNode> getChildren() {
        return Collections.emptyList();
    }
}
