package net.rptools.maptool.dice.result;

import net.rptools.maptool.dice.InvalidExprOperation;

public class BinaryDiceResultOperatorNode implements  DiceResultNode {


    /** The operator that this represents. */
    private final String operator;


    /** The result of the expression. */
    private DiceExprResult diceExprResult;

    /** The node representing what is on the left of the operand. */
    private DiceResultNode left = null;

    /** The node representing what is on the right of the operand. */
    private DiceResultNode right = null;



    protected BinaryDiceResultOperatorNode(String op) {
        operator = op;
    }

    @Override
    public DiceExprResult getExprResult() {
        return diceExprResult;
    }

    @Override
    public String getFormatedText() {
        return left.getFormatedText() + " " + operator + " " + right.getFormatedText();
    }

    @Override
    public void addChild(DiceResultNode child) throws InvalidExprOperation {
        if (left == null) {
            left = child;
        } else if (right == null) {
            right = child;
            evaluate();
        } else {
            throw new UnsupportedOperationException("Operator: " + operator + " only supports two children.");
        }
    }

    private void evaluate() throws InvalidExprOperation {
        switch (operator) {
            case "+":
                diceExprResult = DiceExprResult.add(left.getExprResult(), right.getExprResult());
                break;
            case "-":
                diceExprResult = DiceExprResult.subtract(left.getExprResult(), right.getExprResult());
                break;
            case "*":
                diceExprResult = DiceExprResult.multiply(left.getExprResult(), right.getExprResult());
                break;
            case "/":
                diceExprResult = DiceExprResult.divide(left.getExprResult(), right.getExprResult());
                break;
        }
    }
}
