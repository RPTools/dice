package net.rptools.maptool.dice.roller;

import net.rptools.maptool.dice.DiceExprResultOld;

/**
 * Interface implemented by all dice rollers that are used.
 */
public interface DiceRoller {
    /**
     * Roll the dice and return the result as a {@link DiceExprResultOld}.
     * @param pattern The dice pattern that was used.
     * @param numDice The number of dice to roll.
     * @param numSides The number of sides for the dice.
     * @return a {@link DiceExprResultOld} containing the result and details of the dice roll.
     */
    DiceExprResultOld roll(String pattern, int numDice, int numSides);
}
