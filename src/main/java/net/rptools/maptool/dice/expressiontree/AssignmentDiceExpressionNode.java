package net.rptools.maptool.dice.result.tree;

import net.rptools.maptool.dice.result.DiceExprResult;
import net.rptools.maptool.dice.symbols.DiceEvalScope;
import net.rptools.maptool.dice.symbols.DiceExpressionSymbolTable;

import java.util.Collection;
import java.util.Collections;

/**
 * Represents the node used to assign a value to a symbol.
 */
public class AssignmentDiceExpressionNode implements DiceExpressionNode {


    /** The name of the symbol. */
    private final String name;

    /** The scope of the symbol. */
    private final DiceEvalScope scope;


    /** The right hand side of the assignment expression. */
    private final DiceExpressionNode rhs;


    /** The evaluated value of this node. */
    private DiceExprResult result;

    /**
     * Creates a new node used to assign a value to a symbol.
     * @param symbol The name of the symbol.
     * @param symbolScope The scope of the symbol.
     * @param rhs The right hand side of the assignment.
     */
    public AssignmentDiceExpressionNode(String symbol, DiceEvalScope symbolScope, DiceExpressionNode rhs) {
        name = symbol;
        scope = symbolScope;
        this.rhs = rhs;
    }

    @Override
    public DiceExprResult evaluate(DiceExpressionSymbolTable symbolTable) throws UnsupportedOperationException {
        result = rhs.evaluate(symbolTable);
        symbolTable.setVariableValue(scope, name, result);
        return result;
    }

    @Override
    public DiceExprResult getExprResult() {
        return result;
    }

    @Override
    public String getFormattedText() {
        System.out.println("############ " + name);
        return name + " = " + rhs.getFormattedText();
    }

    @Override
    public Collection<DiceExpressionNode> getChildren() {
        return Collections.singletonList(rhs);
    }

}
