package net.rptools.maptool.dice.roller;

import net.rptools.maptool.dice.result.DiceExprResult;
import net.rptools.maptool.dice.result.DiceRolls;
import net.rptools.maptool.dice.result.DieRoll;

import java.util.ArrayList;

/**
 * The standard dice roller.
 */
@DiceRollerDefinition(name="Standard Dice Roller", patterns = { "d", "D" }, description = "Just the Standard Dice Roller")
public class StandardDiceRoller implements DiceRoller {


    @Override
    public DiceExprResult roll(String pattern, int numDice, int numSides) {
        int[] rolls = DiceUtil.getInstance().randomDiceRollsArray(numDice, numSides);


        int sum = 0;

        var dieRolls = new ArrayList<DieRoll>(rolls.length);
        for (int val : rolls) {
            sum += val;
            dieRolls.add(new DieRoll(val));
        }

        return new DiceExprResult(sum, new DiceRolls(dieRolls, numSides, DiceExprResult.getIntResult(sum), pattern));
    }
}
