package net.rptools.maptool.dice;


import net.rptools.maptool.dice.symbols.DiceEvalType;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class represents the results of expression evaluation.
 */
public class DiceExprResultOld {


    /** The type of the result. */
    private final DiceEvalType type;

    /** The result as an integer (if valid). */
    private final int  intResult;

    /** The result as an number (if valid). */
    private final double  doubleResult;

    /** The result as an string. */
    private final String  stringResult;

    /** The rolls that make up this result (if any). */
    private int[] dieRolls;

    /** Result with an "undefined" value. */
    public static final DiceExprResultOld UNDEFINED = new DiceExprResultOld();

    /** Zero result. */
    public static final DiceExprResultOld ZERO = new DiceExprResultOld(0);

    /** Result for 1. */
    public static final DiceExprResultOld ONE = new DiceExprResultOld(1);

    /**
     * Returns the promoted {@link DiceEvalType} as a result of a binary operation.
     * @param left The type to the left of the operator.
     * @param right The type to the right of the operator.
     * @return The promoted type.
     */
    private static DiceEvalType getOpResultType(DiceExprResultOld left, DiceExprResultOld right) {
        if (left.getType() == DiceEvalType.UNDEFINED || right.getType() == DiceEvalType.UNDEFINED) {
            return DiceEvalType.UNDEFINED;
        }

        switch (left.getType()) {
            case INTEGER:
                switch (right.getType()) {
                    case INTEGER:
                        return DiceEvalType.INTEGER;
                    case DOUBLE:
                        return DiceEvalType.DOUBLE;
                    case STRING:
                        return DiceEvalType.STRING;
                }
            case DOUBLE:
                switch (right.getType()) {
                    case INTEGER:
                    case DOUBLE:
                        return DiceEvalType.DOUBLE;
                    case STRING:
                        return DiceEvalType.STRING;
                }
            case STRING:
            default:
                return DiceEvalType.STRING;
        }
    }

    /**
     * Concatenate the rolls of two results.
     * @param left The rolls for the result on the left hand side of the concatenation operator.
     * @param right The rolls for the result on the right hand side of the concatenation operator.
     * @return The concatenated rolls.
     */
    private static int[] concatenateRolls(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        for (int i = 0; i < left.length; i++) {
            result[i] = left[i];
        }

        for (int i = 0; i < right.length; i++) {
            result[left.length + i] = right[i];
        }

        return result;
    }

    /**
     * Add two dice results together.
     * @param left the result to the left of the addition operator.
     * @param right the result to the right of the addition operator.
     * @return the result of the addition.
     */
    public static DiceExprResultOld add(DiceExprResultOld left, DiceExprResultOld right) {
        switch (getOpResultType(left, right)) {
            case INTEGER:
                return new DiceExprResultOld(
                        left.getIntResult() +  right.getIntResult(),
                        concatenateRolls(left.getDieRolls(), right.getDieRolls())
                );
            case DOUBLE:
                return new DiceExprResultOld(
                        left.getDoubleResult() +  right.getDoubleResult(),
                        concatenateRolls(left.getDieRolls(), right.getDieRolls())
                );
            case STRING:
                return new DiceExprResultOld(
                        left.getStringResult() +  right.getStringResult(),
                        concatenateRolls(left.getDieRolls(), right.getDieRolls())
                );
            default:
                return UNDEFINED;
        }
    }

    /**
     * Subtract one dice result from another.
     * @param left the result to the left of the subtraction operator.
     * @param right the result to the right of the subtraction operator.
     * @return the result of the addition.
     */
    public static DiceExprResultOld subtract(DiceExprResultOld left, DiceExprResultOld right) {
        switch (getOpResultType(left, right)) {
            case INTEGER:
                return new DiceExprResultOld(
                        left.getIntResult() -  right.getIntResult(),
                        concatenateRolls(left.getDieRolls(), right.getDieRolls())
                );
            case DOUBLE:
                return new DiceExprResultOld(
                        left.getDoubleResult() -  right.getDoubleResult(),
                        concatenateRolls(left.getDieRolls(), right.getDieRolls())
                );
            case STRING:
                throw new UnsupportedOperationException("Can not subtract strings"); // TODO
            default:
                return UNDEFINED;
        }
    }

    /**
     * Multiply two dice results together.
     * @param left the result to the left of the multiplication operator.
     * @param right the result to the right of the multiplication operator.
     * @return the result of the multiplication.
     */
    public static DiceExprResultOld multiply(DiceExprResultOld left, DiceExprResultOld right) {
        switch (getOpResultType(left, right)) {
            case INTEGER:
                return new DiceExprResultOld(
                        left.getIntResult() * right.getIntResult(),
                        concatenateRolls(left.getDieRolls(), right.getDieRolls())
                );
            case DOUBLE:
                return new DiceExprResultOld(
                        left.getDoubleResult() * right.getDoubleResult(),
                        concatenateRolls(left.getDieRolls(), right.getDieRolls())
                );
            case STRING:
                throw new UnsupportedOperationException("Can not multiply strings"); // TODO
            default:
                return UNDEFINED;
        }
    }

    /**
     * Divide one dice result by another.
     * @param left the result to the left of the division operator.
     * @param right the result to the right of the division operator.
     * @return the result of the addition.
     */
    public static DiceExprResultOld divide(DiceExprResultOld left, DiceExprResultOld right) {
        switch (getOpResultType(left, right)) {
            case INTEGER:
                return new DiceExprResultOld(
                        left.getIntResult() / right.getIntResult(),
                        concatenateRolls(left.getDieRolls(), right.getDieRolls())
                );
            case DOUBLE:
                return new DiceExprResultOld(
                        left.getDoubleResult() / right.getDoubleResult(),
                        concatenateRolls(left.getDieRolls(), right.getDieRolls())
                );
            case STRING:
                throw new UnsupportedOperationException("Can not divide strings"); // TODO
            default:
                return UNDEFINED;
        }
    }

    /**
     * Negate a dice roll result.
     * @param val the value to negate.
     * @return the negated result.
     */
    public static DiceExprResultOld negate(DiceExprResultOld val) {
        if (val.getType() == DiceEvalType.INTEGER) {
            return new DiceExprResultOld(- val.getIntResult(), val.getDieRolls());
        } else if (val.getType() == DiceEvalType.DOUBLE) {
            return new DiceExprResultOld(-val.getDoubleResult(), val.getDieRolls());
        } else if (val.getType() == DiceEvalType.UNDEFINED) {
            return val;
        } else {
            throw new UnsupportedOperationException("Can not negate strings"); // TODO
        }
    }

    /**
     * Create a DiceExprResultOld with no defined value.
     */
    private DiceExprResultOld() {
        type = DiceEvalType.UNDEFINED;
        doubleResult = Double.NaN;
        intResult = Integer.MIN_VALUE;
        stringResult = "<<undefined>>";
        dieRolls = new int[0];
    }

    /**
     * Create a DiceExprResultOld representing the specified double value and rolls.
     * @param val the result of the roll.
     * @param rolls the rolls that make up the result.
     */
    public DiceExprResultOld(double val, int[] rolls) {
        type = DiceEvalType.DOUBLE;
        doubleResult = val;
        intResult = (int)val;
        stringResult = Double.toString(val);
        dieRolls = new int[rolls.length];
        for (int i = 0; i < rolls.length; i++) {
            dieRolls[i] = rolls[i];
        }
    }

    /**
     * Create a DiceExprResultOld representing the specified integer value and rolls.
     * @param val the result of the roll.
     * @param rolls the rolls that make up the result.
     */
    public DiceExprResultOld(int val, int[] rolls) {
        type = DiceEvalType.INTEGER;
        doubleResult = val;
        intResult = val;
        stringResult = Integer.toString(val);
        dieRolls = new int[rolls.length];
        for (int i = 0; i < rolls.length; i++) {
            dieRolls[i] = rolls[i];
        }
    }

    /**
     * Create a DiceExprResultOld representing the specified string value and rolls.
     * @param val the result of the roll.
     * @param rolls the rolls that make up the result.
     */
    public DiceExprResultOld(String val, int[] rolls) {
        type = DiceEvalType.STRING;
        doubleResult = 0;
        intResult = 0;
        stringResult = val;
        dieRolls = new int[rolls.length];
        for (int i = 0; i < rolls.length; i++) {
            dieRolls[i] = rolls[i];
        }
    }

    /**
     * Create a DiceExprResultOld representing a double.
     * @param val the value of the result.
     */
    public DiceExprResultOld(double val) {
        this(val, new int[0]);
    }

    /**
     * Create a DiceExprResultOld representing an int.
     * @param val the value of the result.
     */
    public DiceExprResultOld(int val) {
        this(val, new int[0]);
    }


    /**
     * Create a DiceExprResultOld representing a string..
     * @param val the value of the result.
     */
    public DiceExprResultOld(String val) {
        this(val, new int[0]);
    }

    /**
     * Returns the type of the result.
     * @return the type of the result.
     */
    public DiceEvalType getType() {
        return type;
    }

    /**
     * Gets the integer version of the result.
     * @return the integer version of the result.
     */
    public int getIntResult() {
        return intResult;
    }

    public double getDoubleResult() {
        return doubleResult;
    }

    public String getStringResult() {
        return stringResult;
    }

    public int[] getDieRolls() {
        return dieRolls;
    }

    @Override
    public String toString() {
        return getStringResult() + "(rolls = [ " +
                IntStream.of(getDieRolls()).mapToObj(Integer::toString).collect(Collectors.joining(", ")) +
                        " ] )";
    }
}
