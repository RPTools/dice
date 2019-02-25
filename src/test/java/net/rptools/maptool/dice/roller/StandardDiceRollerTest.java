package net.rptools.maptool.dice.roller;

import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class StandardDiceRollerTest {

    private static final Random random = new SecureRandom();

    @Test
    void roll() {
        DiceRoller dr = new StandardDiceRoller();

        random.ints(100, 1, 100).forEach( numDice -> {
            random.ints(100, 2, 100).forEach( numSides -> {
                var rolls = dr.roll("d", numDice, numSides).getDiceRolls();

                assertEquals(rolls.getNumberOfRolls(), numDice);
                assertEquals(rolls.getNumberOfSides(), numSides);

                rolls = dr.roll("D", numDice, numSides).getDiceRolls();

                assertEquals(rolls.getNumberOfRolls(), numDice);
                assertEquals(rolls.getNumberOfSides(), numSides);


            });
        });
    }


    @Test
    void rollOneSided() {
        DiceRoller dr = new StandardDiceRoller();

        random.ints(100, 1, 100).forEach( numDice -> {
            var rolls = dr.roll("d", numDice, 1).getDiceRolls();

            assertEquals(rolls.getNumberOfSides(), 1);
            assertEquals(rolls.getNumberOfRolls(), numDice);
            assertEquals(rolls.getResult().getIntResult().orElse(-1), numDice);
        });
    }

}