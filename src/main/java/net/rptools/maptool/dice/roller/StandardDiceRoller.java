package net.rptools.maptool.dice.roller;

import net.rptools.maptool.dice.result.DiceExprResult;
import net.rptools.maptool.dice.result.DiceRolls;
import net.rptools.maptool.dice.result.DieRoll;

import java.security.SecureRandom;
import java.util.ArrayList;
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
    public DiceExprResult roll(String pattern, int numDice, int numSides) {
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

        var dieRolls = new ArrayList<DieRoll>(rolls.length);
        for (int val : rolls) {
            sum += val;
            dieRolls.add(new DieRoll(val));
        }

        return new DiceExprResult(sum, new DiceRolls(dieRolls));
    }
}
