/*
 * This software Copyright by the RPTools.net development team, and licensed under the Affero GPL Version 3 or, at your option, any later version.
 *
 * MapTool Source Code is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU Affero General Public License * along with this source Code. If not, please visit <http://www.gnu.org/licenses/> and specifically the Affero license text
 * at <http://www.gnu.org/licenses/agpl.html>.
 */
package net.rptools.maptool.dice.symbols;

import net.rptools.maptool.dice.result.DiceExprResult;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DefaultDiceExpressionSymbolTableTest {

	private static final Random random = new SecureRandom();

	@Test
	void getSetVariableValue() {
		DefaultDiceExpressionSymbolTable stable = new DefaultDiceExpressionSymbolTable();
		random.ints(100, 1, 100).forEach(r -> {
			DiceExprResult dr1 = DiceExprResult.getIntResult(r);
			DiceExprResult dr2 = DiceExprResult.getIntResult(r + 1);
			DiceExprResult dr3 = DiceExprResult.getIntResult(r + 2);
			DiceExprResult dr4 = DiceExprResult.getIntResult(r + 3);
			DiceExprResult dr5 = DiceExprResult.getIntResult(r + 4);
			DiceExprResult dr6 = DiceExprResult.getIntResult(r + 5);

			stable.setVariableValue(DiceEvalScope.LOCAL, "test1", dr1);
			stable.setVariableValue(DiceEvalScope.GLOBAL, "test1", dr2);
			stable.setVariableValue(DiceEvalScope.PROPERTY, "test1", dr3);

			stable.setVariableValue(DiceEvalScope.LOCAL, "test2", dr4);
			stable.setVariableValue(DiceEvalScope.GLOBAL, "test2", dr5);
			stable.setVariableValue(DiceEvalScope.PROPERTY, "test2", dr6);

			assertEquals(dr1, stable.getVariableValue(DiceEvalScope.LOCAL, "test1"));
			assertEquals(dr2, stable.getVariableValue(DiceEvalScope.GLOBAL, "test1"));
			assertEquals(dr3, stable.getVariableValue(DiceEvalScope.PROPERTY, "test1"));

			assertEquals(dr4, stable.getVariableValue(DiceEvalScope.LOCAL, "test2"));
			assertEquals(dr5, stable.getVariableValue(DiceEvalScope.GLOBAL, "test2"));
			assertEquals(dr6, stable.getVariableValue(DiceEvalScope.PROPERTY, "test2"));
		});

		assertTrue(stable.getVariableValue(DiceEvalScope.LOCAL, "test3") == null);
		assertTrue(stable.getVariableValue(DiceEvalScope.GLOBAL, "test3") == null);
		assertTrue(stable.getVariableValue(DiceEvalScope.PROPERTY, "test3") == null);
	}

	@Test
	void containsVariable() {
		DefaultDiceExpressionSymbolTable stable = new DefaultDiceExpressionSymbolTable();
		random.ints(10, 1, 100).forEach(r -> {
			DiceExprResult dr1 = DiceExprResult.getIntResult(r);
			DiceExprResult dr2 = DiceExprResult.getIntResult(r + 1);
			DiceExprResult dr3 = DiceExprResult.getIntResult(r + 2);
			DiceExprResult dr4 = DiceExprResult.getIntResult(r + 3);
			DiceExprResult dr5 = DiceExprResult.getIntResult(r + 4);
			DiceExprResult dr6 = DiceExprResult.getIntResult(r + 5);

			stable.setVariableValue(DiceEvalScope.LOCAL, "test1", dr1);
			stable.setVariableValue(DiceEvalScope.GLOBAL, "test1", dr2);
			stable.setVariableValue(DiceEvalScope.PROPERTY, "test1", dr3);

			stable.setVariableValue(DiceEvalScope.LOCAL, "test2", dr4);
			stable.setVariableValue(DiceEvalScope.GLOBAL, "test2", dr5);
			stable.setVariableValue(DiceEvalScope.PROPERTY, "test2", dr6);

			assertFalse(stable.getVariableValue(DiceEvalScope.LOCAL, "test1") == null);
			assertFalse(stable.getVariableValue(DiceEvalScope.GLOBAL, "test1") == null);
			assertFalse(stable.getVariableValue(DiceEvalScope.PROPERTY, "test1") == null);

			assertFalse(stable.getVariableValue(DiceEvalScope.LOCAL, "test2") == null);
			assertFalse(stable.getVariableValue(DiceEvalScope.GLOBAL, "test2") == null);
			assertFalse(stable.getVariableValue(DiceEvalScope.PROPERTY, "test2") == null);
		});

		assertTrue(stable.getVariableValue(DiceEvalScope.LOCAL, "test3") == null);
		assertTrue(stable.getVariableValue(DiceEvalScope.GLOBAL, "test3") == null);
		assertTrue(stable.getVariableValue(DiceEvalScope.PROPERTY, "test3") == null);
	}

	@Test
	void getAndContainsVariableNames() {
		DefaultDiceExpressionSymbolTable stable = new DefaultDiceExpressionSymbolTable();
		random.ints(1, 1, 100).forEach(r -> {
			DiceExprResult dr1 = DiceExprResult.getIntResult(r);
			DiceExprResult dr2 = DiceExprResult.getIntResult(r + 1);
			DiceExprResult dr3 = DiceExprResult.getIntResult(r + 2);
			DiceExprResult dr4 = DiceExprResult.getIntResult(r + 3);
			DiceExprResult dr5 = DiceExprResult.getIntResult(r + 4);
			DiceExprResult dr6 = DiceExprResult.getIntResult(r + 5);

			stable.setVariableValue(DiceEvalScope.LOCAL, "test1", dr1);
			stable.setVariableValue(DiceEvalScope.GLOBAL, "test2", dr2);
			stable.setVariableValue(DiceEvalScope.PROPERTY, "test3", dr3);

			stable.setVariableValue(DiceEvalScope.LOCAL, "test4", dr4);
			stable.setVariableValue(DiceEvalScope.GLOBAL, "test5", dr5);
			stable.setVariableValue(DiceEvalScope.PROPERTY, "test6", dr6);
		});

		assertEquals(stable.getVariableNames(DiceEvalScope.LOCAL).size(), 2);
		assertEquals(stable.getVariableNames(DiceEvalScope.GLOBAL).size(), 2);
		assertEquals(stable.getVariableNames(DiceEvalScope.PROPERTY).size(), 2);

		assertTrue(stable.getVariableNames(DiceEvalScope.LOCAL).contains("test1"));
		assertTrue(stable.getVariableNames(DiceEvalScope.GLOBAL).contains("test2"));
		assertTrue(stable.getVariableNames(DiceEvalScope.PROPERTY).contains("test3"));
		assertTrue(stable.getVariableNames(DiceEvalScope.LOCAL).contains("test4"));
		assertTrue(stable.getVariableNames(DiceEvalScope.GLOBAL).contains("test5"));
		assertTrue(stable.getVariableNames(DiceEvalScope.PROPERTY).contains("test3"));

		assertTrue(stable.containsVariable(DiceEvalScope.LOCAL, "test1"));
		assertTrue(stable.containsVariable(DiceEvalScope.GLOBAL, "test2"));
		assertTrue(stable.containsVariable(DiceEvalScope.PROPERTY, "test3"));
		assertTrue(stable.containsVariable(DiceEvalScope.LOCAL, "test4"));
		assertTrue(stable.containsVariable(DiceEvalScope.GLOBAL, "test5"));
		assertTrue(stable.containsVariable(DiceEvalScope.PROPERTY, "test3"));
	}
}