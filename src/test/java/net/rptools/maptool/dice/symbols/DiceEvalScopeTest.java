/*
 * This software Copyright by the RPTools.net development team, and licensed under the Affero GPL Version 3 or, at your option, any later version.
 *
 * MapTool Source Code is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU Affero General Public License * along with this source Code. If not, please visit <http://www.gnu.org/licenses/> and specifically the Affero license text
 * at <http://www.gnu.org/licenses/agpl.html>.
 */
package net.rptools.maptool.dice.symbols;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceEvalScopeTest {

	@Test
	void getScopeName() {
		assertEquals(DiceEvalScope.LOCAL.getScopeName(), "local");
		assertEquals(DiceEvalScope.GLOBAL.getScopeName(), "global");
		assertEquals(DiceEvalScope.PROPERTY.getScopeName(), "property");
	}

	@Test
	void getScopePrefix() {
		assertEquals(DiceEvalScope.LOCAL.getScopePrefix(), "$");
		assertEquals(DiceEvalScope.GLOBAL.getScopePrefix(), "#");
		assertEquals(DiceEvalScope.PROPERTY.getScopePrefix(), "@");
	}

	@Test
	void getScopeForPrefix() {
		assertEquals(DiceEvalScope.getScopeForPrefix("$"), DiceEvalScope.LOCAL);
		assertEquals(DiceEvalScope.getScopeForPrefix("#"), DiceEvalScope.GLOBAL);
		assertEquals(DiceEvalScope.getScopeForPrefix("@"), DiceEvalScope.PROPERTY);

		assertThrows(IllegalArgumentException.class, () -> {
			DiceEvalScope.getScopeForPrefix(":::::!");
		});
	}
}