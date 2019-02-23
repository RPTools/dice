package net.rptools.maptool.dice.result;

import net.rptools.maptool.dice.symbols.DiceExpressionSymbolTable;

import java.util.Collection;
import java.util.Collections;

/**
 * Note which represents a value expression
 */
public class ValueDiceExpressionNode implements DiceExpressionNode {

    /** The value that this object represents. */
    final private DiceExprResult value;

    /**
     * Creates an integer value.
     * @param value the integer.
     */
    ValueDiceExpressionNode(int value) {
        this.value = DiceExprResult.getIntResult(value);
    }

    /**
     * Creates a double value.
     * @param value the doulbe.
     */
    ValueDiceExpressionNode(double value) {
        this.value = DiceExprResult.getDoubleResult(value);
    }

    /**
     * Creates a String value.
     * @param value the String.
     */
    ValueDiceExpressionNode(String value) {
        this.value = DiceExprResult.getStringResult(value);
    }



    @Override
    public DiceExprResult evaluate(DiceExpressionSymbolTable symbolTable) throws UnsupportedOperationException {
        return value;
    }

    @Override
    public DiceExprResult getExprResult() {
        return value;
    }

    @Override
    public String getFormattedText() {
        return value.getStringResult();
    }

    @Override
    public Collection<DiceExpressionNode> getChildren() {
        return Collections.emptyList();
    }

}
