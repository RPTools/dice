package net.rptools.maptool.dice.result;


import net.rptools.maptool.dice.InvalidExprOperation;

import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.zip.DeflaterInputStream;

public final class DiceExprResult {

    /** The type of the result. */
    private final DiceResultType type;

    /** The result as an integer (if valid). */
    private final int intResult;

    /** The result as an number (if valid). */
    private final double doubleResult;

    /** The result as an string. */
    private final String stringResult;

    /** Can this result be numeric. */
    private final boolean hasNumericRepresentation;


    /**
     * Creates an Integer result.
     * @param result the integer value of the result.
     */
    private DiceExprResult(int result) {
        type = DiceResultType.INTEGER;
        intResult = result;
        doubleResult = result;
        stringResult = Integer.toString(result);
        hasNumericRepresentation = true;
    }

    /**
     * Creates a Double result.
     * @param result the double representation of the result.
     */
    private DiceExprResult(double result) {
        type = DiceResultType.DOUBLE;
        intResult = (int) result;
        doubleResult = result;
        stringResult = Double.toString(result);
        hasNumericRepresentation = true;
    }

    /**
     * Creates a String result.
     * @param result the String representation of the result.
     */
    private DiceExprResult(String result) {
        type = DiceResultType.STRING;
        stringResult = result;
        double dres = 0.0;
        boolean numeric;
        try {
            dres = Double.parseDouble(result);
            numeric = true;
        } catch (NumberFormatException nfe) {
            numeric = false;
        }

        hasNumericRepresentation = numeric;
        doubleResult = dres;
        intResult = (int) dres;
    }




    /**
     * Gets the type of the result.
     * @return the type of the result.
     */
    public DiceResultType getType() {
        return type;
    }

    /**
     * Gets the integer representation of the result.
     * @return the integer representation of the result.
     */
    public OptionalInt getIntResult() {
        return hasNumericRepresentation ? OptionalInt.of(intResult) : OptionalInt.empty();
    }

    /**
     * Gets the double representation of the result.
     * @return the double representation of the result.
     */
    public OptionalDouble getDoubleResult() {
        return hasNumericRepresentation ? OptionalDouble.of(doubleResult) : OptionalDouble.empty();
    }

    /**
     * Gets the string representation of the result.
     * @return the string representation of the result.
     */
    public String getStringResult() {
        return stringResult;
    }


    /**
     * Gets a <code>DiceExprResult</code> that is for an integer value.
     * @param res the integer value of the result.
     * @return the <code>DiceExprResult</code> representing the integer value.
     */
    public static DiceExprResult getIntResult(int res) {
        return new DiceExprResult(res);
    }

    /**
     * Gets a <code>DiceExprResult</code> that is for a double value.
     * @param res the double value of the result.
     * @return the <code>DiceExprResult</code> representing the double value.
     */
    public static DiceExprResult getDoubleResult(double res) {
        return new DiceExprResult(res);
    }


    /**
     * Gets a <code>DiceExprResult</code> that is for a String value.
     * @param res the String value of the result.
     * @return the <code>DiceExprResult</code> representing the String value.
     */
    public static DiceExprResult getStringResult(String res) {
        return new DiceExprResult(res);
    }

   /**
     * Add two results together coercing types as needed.
     * @param left the result to the left of the addition operand.
     * @param right the result to the right of the addition operand.
     * @return the result of the addition.
     * @throws InvalidExprOperation when either <code>left</code> or <code>right</code> is {@link DiceResultType#UNDEFINED}
     */
    public static DiceExprResult add(DiceExprResult left, DiceExprResult right) throws InvalidExprOperation {
        if (left.getType() == DiceResultType.UNDEFINED) {
            throw new InvalidExprOperation("Left hand side of addition is undefined.");
        }
        if (right.getType() == DiceResultType.UNDEFINED) {
            throw new InvalidExprOperation("Right hand side of addition is undefined.");
        }

        if (left.getType() == DiceResultType.STRING || right.getType() == DiceResultType.STRING) {
            return getStringResult(left.getStringResult() + right.getStringResult());
        } else if (left.getType() == DiceResultType.DOUBLE || right.getType() == DiceResultType.DOUBLE) {
            return getDoubleResult(left.getDoubleResult().getAsDouble() + right.getDoubleResult().getAsDouble());
        } else {
            return getIntResult(left.getIntResult().getAsInt() + right.getIntResult().getAsInt());
        }
    }

    /**
     * Subtract one result from another coercing types as needed.
     * @param left the result to the left of the subtraction operand.
     * @param right the result to the right of the subtraction operand.
     * @return the result of the subtraction.
     * @throws InvalidExprOperation when either <code>left</code> or <code>right</code> is
     *                              {@link DiceResultType#UNDEFINED} or mixing numbers and strings.
     */
    public static DiceExprResult subtract(DiceExprResult left, DiceExprResult right) throws InvalidExprOperation {
        if (left.getType() == DiceResultType.UNDEFINED) {
            throw new InvalidExprOperation("Left hand side of subtraction is undefined.");
        }
        if (right.getType() == DiceResultType.UNDEFINED) {
            throw new InvalidExprOperation("Right hand side of subtraction is undefined.");
        }

        if (left.getType() == DiceResultType.STRING && right.getType() == DiceResultType.STRING) {
            return getStringResult(left.getStringResult().replace(right.getStringResult(), ""));
        } else if (left.getType() == DiceResultType.STRING) {
            throw new InvalidExprOperation("Can not subtract a number from a string");
        } else if (right.getType() == DiceResultType.STRING) {
            throw new InvalidExprOperation("Can not subtract a string from a number");
        } else if (left.getType() == DiceResultType.DOUBLE || right.getType() == DiceResultType.DOUBLE) {
            return getDoubleResult(left.getDoubleResult().getAsDouble() - right.getDoubleResult().getAsDouble());
        } else {
            return getIntResult(left.getIntResult().getAsInt() - right.getIntResult().getAsInt());
        }
    }

    /**
     * Multiply two results together coercing types as needed.
     * @param left the result to the left of the multiplication operand.
     * @param right the result to the right of the multiplication operand.
     * @return the result of the multiplication.
     * @throws InvalidExprOperation when either <code>left</code> or <code>right</code> is
     *                              {@link DiceResultType#UNDEFINED} or both arguments are strings.
     */
    public static DiceExprResult multiply(DiceExprResult left, DiceExprResult right) throws InvalidExprOperation {
        if (left.getType() == DiceResultType.UNDEFINED) {
            throw new InvalidExprOperation("Left hand side of multiplication is undefined.");
        }
        if (right.getType() == DiceResultType.UNDEFINED) {
            throw new InvalidExprOperation("Right hand side of multiplication is undefined.");
        }

        if (left.getType() == DiceResultType.STRING && right.getType() == DiceResultType.STRING) {
            throw new InvalidExprOperation("You can not multiply two strings.");
        } else if (left.getType() == DiceResultType.STRING || right.getType() == DiceResultType.STRING) {
            int times;
            String str;

            if (left.getType() == DiceResultType.STRING) {
                times = right.getIntResult().getAsInt();
                str = left.getStringResult();
            } else {
                times = left.getIntResult().getAsInt();
                str = right.getStringResult();
            }
            StringBuffer sb = new StringBuffer();
            sb.ensureCapacity(times * str.length());
            for (int i = 0; i < times; i++) {
                sb.append(str);
            }
            return getStringResult(sb.toString());
        } else if (left.getType() == DiceResultType.DOUBLE || right.getType() == DiceResultType.DOUBLE) {
            return getDoubleResult(left.getDoubleResult().getAsDouble() * right.getDoubleResult().getAsDouble());
        } else {
            return getIntResult(left.getIntResult().getAsInt() * right.getIntResult().getAsInt());
        }
    }

    /**
     * Divide one result from another coercing types as needed.
     * @param left the result to the left of the division operand.
     * @param right the result to the right of the division operand.
     * @return the result of the division.
     * @throws InvalidExprOperation when either <code>left</code> or <code>right</code> is
     *                              {@link DiceResultType#UNDEFINED} or a String.
     */
    public static DiceExprResult divide(DiceExprResult left, DiceExprResult right) throws InvalidExprOperation {
        if (left.getType() == DiceResultType.UNDEFINED) {
            throw new InvalidExprOperation("Left hand side of division is undefined.");
        }
        if (right.getType() == DiceResultType.UNDEFINED) {
            throw new InvalidExprOperation("Right hand side of division is undefined.");
        }

        if (left.getType() == DiceResultType.STRING || right.getType() == DiceResultType.STRING) {
            throw new InvalidExprOperation("Strings can not take part in division.");
        } else if (left.getType() == DiceResultType.DOUBLE || right.getType() == DiceResultType.DOUBLE) {
            return getDoubleResult(left.getDoubleResult().getAsDouble() / right.getDoubleResult().getAsDouble());
        } else {
            return getIntResult(left.getIntResult().getAsInt() / right.getIntResult().getAsInt());
        }
    }
}
