package net.rptools.maptool.dice.roller;

import java.util.Optional;
import java.util.OptionalInt;

/**
 * This class represents modifier arguments to a dice roll.
 * Arguments are of the form <i>name</i>[<i>operator</i>][<i>number</i>]
 */
public class DiceRollerArgument {

    /** The name of the argument. */
    private final String argumentName;

    /** The operator that forms part of the argument. */
    private final String operator;

    /** Any number that forms part of the argument. */
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private final OptionalInt number;


    /**
     * Creates a new dice roller argument.
     * @param name The name of the argument.
     * @param op The operator that is part of the argument.
     * @param num The number that is part of the argument.
     */
    public DiceRollerArgument(String name, String op, int num) {
        argumentName = name;
        operator = op;
        number = OptionalInt.of(num) ;
    }

    /**
     * Creates a new dice roller argument.
     * @param name The name of the argument.
     */
    public DiceRollerArgument(String name) {
        argumentName = name;
        operator = null;
        number = OptionalInt.empty();
    }

    /**
     * Creates a new dice roller argument.
     * @param name The name of the argument.
     * @param num The number that is part of the argument.
     */
    @SuppressWarnings("WeakerAccess")
    public DiceRollerArgument(String name, int num) {
        argumentName = name;
        operator = null;
        number = OptionalInt.of(num);
    }

    /**
     * Returns the argument name.
     * @return the name of the argument.
     */
    @SuppressWarnings("WeakerAccess")
    public String getArgumentName() {
        return argumentName;
    }

    /**
     * Returns the operator if any that was used im the argument..
     * @return the operator if any that was used.
     */
    @SuppressWarnings("WeakerAccess")
    public Optional<String> getOperator() {
        return Optional.ofNullable(operator);
    }

    /**
     * Returns the number for the argument.
     * @return the number for the argument.
     */
    @SuppressWarnings("WeakerAccess")
    public OptionalInt getNumber() {
        return number;
    }

    /**
     * Was no number supplied to the argument.
     * @return <code>true</code> if no number was supplied to the argument and the default should be used.
     */
    @SuppressWarnings("WeakerAccess")
    public boolean useDefaultNumber() {
        return number.isEmpty();
    }

    @Override
    public String toString() {
        if (operator == null) {
            return argumentName + (number.isEmpty() ? "" : " = " + number.getAsInt());
        } else {
            return argumentName + " " + operator + " " + (number.isEmpty() ? "(default)" : number.getAsInt());
        }
    }
}
