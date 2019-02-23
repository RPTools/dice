package net.rptools.maptool.dice.result;


import net.rptools.maptool.dice.symbols.DiceExpressionSymbolTable;

import java.util.Collection;

/**
 * Nodes used to represent different roll expressions during parsing and execution.
 */
public interface DiceExpressionNode {


    /**
     * Evaluate the node.
     * @param symbolTable The symbol table used to resolve symbols.
     *
     * @return The evaluated value of the expression.
     *
     * @throws UnsupportedOperationException if the operation attempting to be performed is invalid.
     */
    DiceExprResult evaluate(DiceExpressionSymbolTable symbolTable) throws UnsupportedOperationException;

    /**
     * Returns the result of the evaluation, this must always remain the same
     *
     * @return the result of the evaluation.
     */
    DiceExprResult getExprResult();

    /**
     * Returns the result as formatted text.
     * @return the result as formatted text.
     */
    String getFormattedText();

    /**
     * Returns the children of this node.
     * @return the children of this code.
     */
    Collection<DiceExpressionNode> getChildren();

}
