/*
 * This software Copyright by the RPTools.net development team, and
 * licensed under the Affero GPL Version 3 or, at your option, any later
 * version.
 *
 * MapTool Source Code is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU Affero General Public
 * License * along with this source Code.  If not, please visit
 * <http://www.gnu.org/licenses/> and specifically the Affero license
 * text at <http://www.gnu.org/licenses/agpl.html>.
 */
package net.rptools.maptool.dice.result;

import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class DiceRollsTest {
    private static final Random random = new SecureRandom();

    /**
     * Helper function to generate a collection of {@link DieRoll}s.
     *
     * @param number
     *            The number of rolls to add.
     * @param sides
     *            The number of sides for the dice.
     * @return a collection of {@link DieRoll}s
     */
    private static Collection<DieRoll> generateRolls(int number, int sides) {

        return random.ints(number, 1, sides + 1)
                .mapToObj(DieRoll::new).collect(Collectors.toList());

    }

    /**
     * Helper function to generate a {@link DiceRolls}s which have been flagged with different statuses.
     *
     * Rolls generated. 1 - fumble, failure 2 - failure 3 - no flags 4 - no flags 5 - success 6 - critical, success
     *
     * @return a {@link DiceRolls} with the above flags.
     */
    private static DiceRolls generateFlaggedRolls() {
        List<DieRoll> dieRolls = new LinkedList<>();
        dieRolls.add(new DieRoll(1, List.of(DieRoll.DieRollFlags.FUMBLE, DieRoll.DieRollFlags.FAILURE)));
        dieRolls.add(new DieRoll(2, Collections.singleton(DieRoll.DieRollFlags.FAILURE)));
        dieRolls.add(new DieRoll(3, Collections.emptyList()));
        dieRolls.add(new DieRoll(4, Collections.emptyList()));
        dieRolls.add(new DieRoll(5, Collections.singleton(DieRoll.DieRollFlags.SUCCESS)));
        dieRolls.add(new DieRoll(6, List.of(DieRoll.DieRollFlags.CRITICAL, DieRoll.DieRollFlags.SUCCESS)));

        return new DiceRolls(dieRolls, 6, DiceExprResult.getIntResult(6), TEST_FLAGGED_ROLL_NAME);
    }

    private static final String TEST_ROLL_NAME = "Test Roll";
    private static final String TEST_FLAGGED_ROLL_NAME = "Test Flagged Roll";

    @Test
    void getDiceRolls() {
        var rolls = generateRolls(100, 6);
        var diceRolls = new DiceRolls(rolls, 6, DiceExprResult.getIntResult(6), TEST_ROLL_NAME);

        assertEquals(diceRolls.getDiceRolls().size(), rolls.size());
        assertTrue(diceRolls.getDiceRolls().containsAll(rolls));

    }

    @Test
    void getSuccesses() {
        var rolls = generateRolls(100, 6);
        var diceRolls = new DiceRolls(rolls, 6, DiceExprResult.getIntResult(6), TEST_ROLL_NAME);

        assertEquals(0, diceRolls.getSuccesses());
        assertEquals(2, generateFlaggedRolls().getSuccesses());
    }

    @Test
    void getFailures() {
        var rolls = generateRolls(100, 6);
        var diceRolls = new DiceRolls(rolls, 6, DiceExprResult.getIntResult(6), TEST_ROLL_NAME);

        assertEquals(0, diceRolls.getFailures());
        assertEquals(2, generateFlaggedRolls().getFailures());
    }

    @Test
    void getCriticals() {
        var rolls = generateRolls(100, 6);
        var diceRolls = new DiceRolls(rolls, 6, DiceExprResult.getIntResult(6), TEST_ROLL_NAME);

        assertEquals(0, diceRolls.getCriticals());
        assertEquals(1, generateFlaggedRolls().getCriticals());
    }

    @Test
    void getFumbles() {
        var rolls = generateRolls(100, 6);
        var diceRolls = new DiceRolls(rolls, 6, DiceExprResult.getIntResult(6), TEST_ROLL_NAME);

        assertEquals(0, diceRolls.getFumbles());
        assertEquals(1, generateFlaggedRolls().getFumbles());
    }

    @Test
    void getNumberOfRolls() {
        var rolls = generateRolls(100, 6);
        var diceRolls = new DiceRolls(rolls, 6, DiceExprResult.getIntResult(6), TEST_ROLL_NAME);

        assertEquals(diceRolls.getNumberOfRolls(), rolls.size());

        diceRolls = generateFlaggedRolls();
        assertEquals(6, diceRolls.getNumberOfRolls());
    }

    @Test
    void getNumberOfSides() {
        var rolls = generateRolls(100, 6);
        var diceRolls = new DiceRolls(rolls, 6, DiceExprResult.getIntResult(6), TEST_ROLL_NAME);

        assertEquals(6, diceRolls.getNumberOfSides());

        diceRolls = generateFlaggedRolls();
        assertEquals(6, diceRolls.getNumberOfSides());

        diceRolls = new DiceRolls(
                Collections.singleton(new DieRoll(6)),
                10,
                DiceExprResult.getIntResult(6),
                TEST_ROLL_NAME);
        assertEquals(10, diceRolls.getNumberOfSides());
    }

    @Test
    void getResult() {
        var diceRolls = new DiceRolls(
                Collections.singleton(new DieRoll(1)),
                10,
                DiceExprResult.getIntResult(1),
                TEST_ROLL_NAME);
        assertEquals(1, diceRolls.getResult().getIntResult().orElse(-999));

        diceRolls = new DiceRolls(
                Collections.singleton(new DieRoll(1)),
                10,
                DiceExprResult.getIntResult(2),
                TEST_ROLL_NAME);
        assertEquals(2, diceRolls.getResult().getIntResult().orElse(-999));

        diceRolls = new DiceRolls(
                Collections.singleton(new DieRoll(1)),
                10,
                DiceExprResult.getIntResult(3),
                TEST_ROLL_NAME);
        assertEquals(3, diceRolls.getResult().getIntResult().orElse(-999));

        diceRolls = new DiceRolls(
                Collections.singleton(new DieRoll(1)),
                10,
                DiceExprResult.getIntResult(6),
                TEST_ROLL_NAME);
        assertEquals(6, diceRolls.getResult().getIntResult().orElse(-999));

    }

    @Test
    void getName() {
        var rolls = generateRolls(105, 7);
        var diceRolls = new DiceRolls(rolls, 7, DiceExprResult.getIntResult(6), TEST_ROLL_NAME);

        assertEquals(diceRolls.getName(), TEST_ROLL_NAME);
        assertEquals(generateFlaggedRolls().getName(), TEST_FLAGGED_ROLL_NAME);
    }
}