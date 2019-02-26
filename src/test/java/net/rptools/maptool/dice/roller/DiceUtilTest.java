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
package net.rptools.maptool.dice.roller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceUtilTest {

    @Test
    void randomInt() {
        for (int upper = 1; upper < 100; upper++) {
            for (int lower = 1; lower <= upper; lower++) {
                int rand = DiceUtil.getInstance().randomInt(lower, upper);
                assertTrue(rand >= lower);
                assertTrue(rand <= upper);
            }
        }
    }

    @Test
    void randomInts() {
        for (int upper = 1; upper < 10; upper++) {
            for (int lower = 1; lower <= upper; lower++) {
                int num = DiceUtil.getInstance().randomInt(1, 100);
                int[] rands = DiceUtil.getInstance().randomInts(num, lower, upper).toArray();
                assertEquals(rands.length, num);
                for (int rand : rands) {
                    assertTrue(rand >= lower);
                    assertTrue(rand <= upper);
                }
            }
        }
    }

    @Test
    void randomIntsArray() {
        for (int upper = 1; upper < 10; upper++) {
            for (int lower = 1; lower <= upper; lower++) {
                int num = DiceUtil.getInstance().randomInt(1, 50);
                int[] rands = DiceUtil.getInstance().randomIntsArray(num, lower, upper);
                assertEquals(rands.length, num);
                for (int rand : rands) {
                    assertTrue(rand >= lower);
                    assertTrue(rand <= upper);
                }
            }
        }
    }

    @Test
    void randomDieRoll() {
        for (int upper = 1; upper < 100; upper++) {
            int rand = DiceUtil.getInstance().randomDieRoll(upper);
            assertTrue(rand >= 1);
            assertTrue(rand <= upper);
        }
    }

    @Test
    void randomDiceRolls() {
        for (int upper = 1; upper < 100; upper++) {
            int num = DiceUtil.getInstance().randomDieRoll(50);
            int[] rands = DiceUtil.getInstance().randomDiceRolls(num, upper).toArray();
            assertEquals(num, rands.length);
            for (int rand : rands) {
                assertTrue(rand >= 1);
                assertTrue(rand <= upper);
            }
        }
    }

    @Test
    void randomDiceRollsArray() {
        for (int upper = 1; upper < 100; upper++) {
            int num = DiceUtil.getInstance().randomDieRoll(50);
            int[] rands = DiceUtil.getInstance().randomDiceRollsArray(num, upper);
            assertEquals(num, rands.length);
            for (int rand : rands) {
                assertTrue(rand >= 1);
                assertTrue(rand <= upper);
            }
        }
    }
}