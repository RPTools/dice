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

import net.rptools.maptool.dice.expressiontree.DiceExpressionNode;
import net.rptools.maptool.dice.result.DieRoll;

public class AddDiceRollerArgument extends AbstractDiceRollerArgument {

  public AddDiceRollerArgument(DiceExpressionNode value) {
    super(value);
  }

  @Override
  public String getArgumentName() {
    return "add";
  }

  @Override
  public DieRoll applyToRoll(DieRoll roll) {
    return new DieRoll(
        roll.getValue()
            + getValue()
                .getIntResult()
                .orElseThrow(
                    () -> new IllegalArgumentException("Can only add numbers to dice rolls.")),
        roll.getFlags(),
        roll.getSides());
  }
}
