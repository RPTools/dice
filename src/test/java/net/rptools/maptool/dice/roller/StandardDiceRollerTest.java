/*
 * This software Copyright by the RPTools.net development team, and licensed under the Affero GPL Version 3 or, at your option, any later version.
 *
 * MapTool Source Code is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU Affero General Public License * along with this source Code. If not, please visit <http://www.gnu.org/licenses/> and specifically the Affero license text
 * at <http://www.gnu.org/licenses/agpl.html>.
 */
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

		random.ints(100, 1, 100).forEach(numDice -> {
			random.ints(100, 2, 100).forEach(numSides -> {
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

		random.ints(100, 1, 100).forEach(numDice -> {
			var rolls = dr.roll("d", numDice, 1).getDiceRolls();

			assertEquals(rolls.getNumberOfSides(), 1);
			assertEquals(rolls.getNumberOfRolls(), numDice);
			assertEquals(rolls.getResult().getIntResult().orElse(-1), numDice);
		});
	}

}