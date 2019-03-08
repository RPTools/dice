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
package net.rptools.dice.roller;

import java.util.ArrayList;
import java.util.Collection;
import net.rptools.dice.result.DiceExprResult;
import net.rptools.dice.result.DiceRolls;
import net.rptools.dice.result.DiceRolls.DiceRollAggregateMethod;
import net.rptools.dice.roller.arguments.DiceRollerArgument;
import net.rptools.dice.result.DieRoll;

/** The standard dice roller. */
@DiceRollerDefinition(
    name = "Standard Dice Roller",
    patterns = {"d", "D", "d2r"},
    description = "Just the Standard Dice Roller")
public class StandardDiceRoller implements DiceRoller {

  @Override
  public DiceExprResult roll(
      String pattern, int numDice, int numSides, Collection<DiceRollerArgument> args) {
    int[] rolls = DiceUtil.getInstance().randomDiceRollsArray(numDice, numSides);

    int sum = 0;

    var dieRolls = new ArrayList<DieRoll>(rolls.length);
    for (int val : rolls) {
      sum += val;
      DieRoll dr = new DieRoll(val, numSides);
      for (DiceRollerArgument a : args) {
        dr = a.applyToRoll(dr);
      }
      dieRolls.add(dr);
    }

    return new DiceExprResult(
        sum, new DiceRolls(dieRolls, numSides, pattern, DiceRollAggregateMethod.SUM));
  }
}
