package net.rptools.maptool.dice.result;

import net.rptools.maptool.dice.symbols.DiceExpressionSymbolTable;

import java.util.Arrays;
import java.util.Collection;

/**
 * Node that represents a binary operation in the dice roll script.
 */
public class BinaryDiceResultOpNode implements  DiceResultNode {


    /** The operator that this represents. */
    private final String operator;


    /** The result of the expression. */
    private DiceExprResult diceExprResult;

    /** The node representing what is on the left of the operator. */
    private final DiceResultNode left;

    /** The node representing what is on the right of the operator. */
    private final  DiceResultNode right;


    /**
     * Creates a new node for a binary operation.
     * @param op The binary operator to represent.
     * @param left What is to the left of the operator.
     * @param right What is to the right of the operator.
     */
    BinaryDiceResultOpNode(String op, DiceResultNode left, DiceResultNode right ) {
        operator = op;
        this.left = left;
        this.right = right;
    }

    @Override
    public DiceExprResult getExprResult() {
        return diceExprResult;
    }

    @Override
    public String getFormattedText() {
        return left.getFormattedText() + " " + operator + " " + right.getFormattedText();
    }

    @Override
    public Collection<DiceResultNode> getChildren() {
        return Arrays.asList(new DiceResultNode[] { left, right });
    }


    @Override
    public DiceExprResult evaluate(DiceExpressionSymbolTable symbolTable) throws UnsupportedOperationException {
        switch (operator) {
            case "+":
                diceExprResult = DiceExprResult.add(left.evaluate(symbolTable), right.evaluate(symbolTable));
                break;
            case "-":
                diceExprResult = DiceExprResult.subtract(left.evaluate(symbolTable), right.evaluate(symbolTable));
                break;
            case "*":
                diceExprResult = DiceExprResult.multiply(left.evaluate(symbolTable), right.evaluate(symbolTable));
                break;
            case "/":
                diceExprResult = DiceExprResult.divide(left.evaluate(symbolTable), right.evaluate(symbolTable));
                break;
            default:
                throw new UnsupportedOperationException("Unknown binary operator: " + operator);
        }
        return diceExprResult;
    }
}
