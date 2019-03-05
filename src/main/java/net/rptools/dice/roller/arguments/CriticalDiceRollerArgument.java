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

import java.util.Arrays;
import java.util.Collections;
import net.rptools.dice.expressiontree.DiceExpressionNode;
import net.rptools.dice.result.DieRoll;

public class CriticalDiceRollerArgument extends AbstractDiceRollerArgument {

  private static final ModifyDieRollFlags modifyDieRollFlags =
      new ModifyDieRollFlags(
          Collections.singleton(DieRoll.DieRollFlags.CRITICAL),
          Arrays.asList(DieRoll.DieRollFlags.FUMBLE, DieRoll.DieRollFlags.FAILURE));

  public CriticalDiceRollerArgument(DiceExpressionNode value, String operator) {
    super(operator, value);
  }

  @Override
  public String getArgumentName() {
    return "critical success";
  }

  @Override
  public DieRoll applyToRoll(DieRoll roll) {
    return modifyDieRollFlags.performTest(roll, getOperator().orElse("="), getValue(), roll.getSides());
  }
}
