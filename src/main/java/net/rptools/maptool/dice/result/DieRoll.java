package net.rptools.maptool.dice.result;


import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Class that represents the result of a single roll.
 */
public class DieRoll {

    /**
     * The different types of flags for a die roll.
     */
    public enum DieRollFlags {
        /** The roll is a critical fumble. */
        FUMBLE,
        /** The roll is a failure. */
        FAILURE,
        /** The roll is a success. */
        SUCCESS,
        /** The roll is a critical. */
        CRITICAL
    }

    /** The value of the roll. */
    private final int value;

    /** Any flags for the roll, such as critical, fumble, etc. */
    private final Set<DieRollFlags> flags;


    /**
     * Creates a new <code>DiceRolls</code> object.
     * @param value The value rolled.
     * @param flags Any flags associated with the roll.
     */
    @SuppressWarnings("WeakerAccess")
    public DieRoll(int value, Collection<DieRollFlags> flags) {
        this.value = value;
        this.flags = Set.copyOf(flags);
    }

    /**
     * Creates a new <code>DiceRolls</code> object.
     * @param value The value rolled.
     */
    public DieRoll(int value) {
        this(value, Collections.emptyList());
    }


    /**
     * Returns the value of the die roll.
     * @return the value of the die roll.
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the flags associated with the die roll.
     * @return the flags associated with the die roll.
     */
    @SuppressWarnings("WeakerAccess")
    public Collection<DieRollFlags> getFlags() {
        return flags;
    }

    /**
     * Returns <code>true</code> if this die roll was flagged as a success.
     *
     * @return <code>true</code> if it was a success.
     */
    public boolean isSuccess() {
        return flags.contains(DieRollFlags.SUCCESS);
    }

    /**
     * Returns <code>true</code> if this die roll was flagged as a failure.
     *
     * @return <code>true</code> if it was a failure.
     */
    public boolean isFailure() {
        return flags.contains(DieRollFlags.FAILURE);
    }

    /**
     * Returns <code>true</code> if this die roll was flagged as a critical.
     *
     * @return <code>true</code> if it was a critical.
     */
    public boolean isCritical() {
        return flags.contains(DieRollFlags.CRITICAL);
    }


    /**
     * Returns <code>true</code> if this die roll was flagged as a fumble.
     *
     * @return <code>true</code> if it was a fumble.
     */
    public boolean isFumble() {
        return flags.contains(DieRollFlags.FUMBLE);
    }


}

