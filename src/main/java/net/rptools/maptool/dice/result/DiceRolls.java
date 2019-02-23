package net.rptools.maptool.dice.result;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * This class holds a collection of {@link DieRoll}s and an aggregation of their flagged values.
 */
public class DiceRolls {

    /** The {@link DieRoll} that make up this list of rolls. */
    private final List<DieRoll> diceRolls;

    /** The number of successes recorded. */
    private final int successes;

    /** The number of failures recorded. */
    private final int failures;

    /** The number of criticals recoded. */
    private final int criticals;

    /** the number of fumbles recorded. */
    private final int fumbles;


    /** The number of rolls performed. */
    private final int numberOfRolls;


    /** No dice were rolled in the create of this value. */
    public static DiceRolls NO_ROLLS = new DiceRolls(Collections.emptyList());

    /**
     * Creates a new <code>DiceRolls</code> to hold a collection of {@link DieRoll}s.
     * @param rolls the {@link DieRoll}s.
     */
    public DiceRolls(Collection<DieRoll> rolls) {
        diceRolls = List.copyOf(rolls);

        int numSuccess = 0;
        int numFail = 0;
        int numCritical = 0;
        int numFumble = 0;

        for (var d : diceRolls) {
            if (d.isFumble()) {
                numFumble++;
            }

            if (d.isFailue()) {
                numFail++;
            }

            if (d.isSuccess()) {
                numSuccess++;
            }

            if (d.isCritical()) {
                numCritical++;
            }
        }

        fumbles = numFumble;
        failures = numFail;
        successes = numSuccess;
        criticals = numCritical;


        numberOfRolls = diceRolls.size();
    }

    /**
     * Returns the {@link DieRoll}s that make up this collection of rolls.
     * @return the {@link DieRoll}s that make up the collection.
     */
    public Collection<DieRoll> getDiceRolls() {
        return diceRolls;
    }

    /**
     * Returns the number of successes recorded in the rolls.
     * @return the number of successs recorded.
     */
    public int getSuccesses() {
        return successes;
    }

    /**
     * Returns the number of failures recorded in the rolls.
     * @return the number of failures recorded.
     */
    public int getFailures() {
        return failures;
    }

    /**
     * Returns the number of criticals recorded in the rolls.
     * @return the number of criticals recorded.
     */
    public int getCriticals() {
        return criticals;
    }

    /**
     * Returns the number of fumbles recorded in the rolls.
     * @return the number of fumbles recorded.
     */
    public int getFumbles() {
        return fumbles;
    }

    /**
     * Returns the number of rolls performed.
     * @return the number of rolls performed.
     */
    public int getNumberOfRolls() {
        return numberOfRolls;
    }
}
