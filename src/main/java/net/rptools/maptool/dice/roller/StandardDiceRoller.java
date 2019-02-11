package net.rptools.maptool.dice.roller;

import net.rptools.maptool.dice.DiceExprResultOld;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

@DiceRollerDefinition(name="Standard Dice Roller", patterns = { "d", "D" }, description = "Just the Standard Dice Roller")
/**
 * The standard dice roller.
 */
public class StandardDiceRoller implements DiceRoller {

    /** {@link Random} class used to perform rolls. */
    private static final Random random = new SecureRandom();


    @Override
    public DiceExprResultOld roll(String pattern, int numDice, int numSides) {
        //final List<Integer> rolls = new LinkedList<>();
        //random.ints(numDice, 1, numSides).forEach(rolls::add);
        int[] rolls;
        if (numSides == 1) {
            rolls = new int[numDice];
            Arrays.fill(rolls, 1);
        } else {
            rolls = random.ints(numDice, 1, numSides + 1).toArray();
        }

        int sum = 0;

        for (int val : rolls) {
            sum += val;
        }

        return new DiceExprResultOld(sum, rolls);
    }
}
