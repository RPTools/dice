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
package net.rptools.maptool.dice.roller.arguments;

import java.util.Optional;
import net.rptools.maptool.dice.result.DiceExprResult;
import net.rptools.maptool.dice.result.DiceRolls;
import net.rptools.maptool.dice.result.DieRoll;
import net.rptools.maptool.dice.symbols.DiceExpressionSymbolTable;

public interface DiceRollerArgument {
  String getArgumentName();

  Optional<String> getOperator();

  DiceExprResult getValue();

  default DieRoll applyToRoll(DieRoll roll) {
    return roll;
  }

  default DiceRolls applyToAll(DiceRolls rolls) {
    return rolls;
  }

  void evaluate(DiceExpressionSymbolTable symbolTable);
}
