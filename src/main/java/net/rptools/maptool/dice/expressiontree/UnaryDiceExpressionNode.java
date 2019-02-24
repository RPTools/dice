package net.rptools.maptool.dice.result.tree;

import net.rptools.maptool.dice.result.DiceExprResult;
import net.rptools.maptool.dice.result.tree.DiceExpressionNode;
import net.rptools.maptool.dice.symbols.DiceExpressionSymbolTable;

import java.util.Collection;
import java.util.Collections;

/**
 * Node that represents a unary operation in the dice roll script.
 */
public class UnaryDiceExpressionNode implements DiceExpressionNode {


    /** The operator that this represents. */
    private final String operator;


    /** The result of the expression. */
    private DiceExprResult diceExprResult;

    /** The node representing the operand. */
    private final DiceExpressionNode operand;


    /** The evaluated value of this node. */
    private DiceExprResult result;


    /**
     * Creates a new node for a unary operation.
     * @param op The unary operator to represent.
     * @param operand The operand for this operator.
     */
    public UnaryDiceExpressionNode(String op, DiceExpressionNode operand) {
        operator = op;
        this.operand = operand;
    }

    @Override
    public DiceExprResult getExprResult() {
        return diceExprResult;
    }

    @Override
    public String getFormattedText() {
        return operator + " " + operand.getFormattedText();
    }

    @Override
    public Collection<DiceExpressionNode> getChildren() {
        return Collections.singletonList(operand);
    }

    public DiceExprResult evaluate(DiceExpressionSymbolTable symbolTable) throws UnsupportedOperationException {
        switch (operator) {
            case "-":
                diceExprResult = DiceExprResult.negate(operand.evaluate(symbolTable));
                break;
            default:
                throw new UnsupportedOperationException("Unknown unary operator: " + operator);
        }
        return null;
    }
}
