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
package net.rptools.dice.roller.arguments;

import java.util.Optional;
import net.rptools.dice.expressiontree.DiceExpressionNode;
import net.rptools.dice.result.DiceExprResult;
import net.rptools.dice.result.DiceRolls;
import net.rptools.dice.symbols.DiceExpressionSymbolTable;
import net.rptools.dice.result.DieRoll;

abstract class AbstractDiceRollerArgument implements DiceRollerArgument {

  private final DiceExpressionNode valueNode;
  private final String operator;

  AbstractDiceRollerArgument(DiceExpressionNode valueNode) {
    this.valueNode = valueNode;
    operator = null;
  }

  AbstractDiceRollerArgument(String op, DiceExpressionNode valueNode) {
    this.valueNode = valueNode;
    operator = op;
  }

  public void evaluate(DiceExpressionSymbolTable symbolTable) {
    if (valueNode != null) {
      valueNode.evaluate(symbolTable);
    }
  }

  public Optional<String> getOperator() {
    return Optional.ofNullable(operator);
  }

  public DiceExprResult getValue() {
    return valueNode == null ? DiceExprResult.UNDEFINED : valueNode.getExprResult();
  }

  public DieRoll applyToRoll(DieRoll roll) {
    return roll;
  }

  public DiceRolls applyToAll(DiceRolls rolls) {
    return rolls;
  }

  public abstract String getArgumentName();
}
