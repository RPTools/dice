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
package net.rptools.dice.expressiontree;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import net.rptools.dice.result.DiceExprResult;
import net.rptools.dice.roller.DiceRollers;
import net.rptools.dice.roller.arguments.DiceRollerArgument;
import net.rptools.dice.symbols.DiceExpressionSymbolTable;

/** None that represents rolling of dice */
public class DiceRollDiceExpressionNode implements DiceExpressionNode {

  /** the number of sides on the dice. * */
  private final DiceExpressionNode numberOfSides;

  /** The number of dice to roll. */
  private final DiceExpressionNode numberOfDice;

  /** The name of the dice. */
  private final String diceName;

  /** The input string for the roll. */
  private final String diceString;

  /** The arguments to the dice roller. */
  private final List<DiceRollerArgument> rollerArguments;

  /** The result of evaluating the expression. */
  private DiceExprResult result;

  /**
   * Create a node to hold a dice roll.
   *
   * @param name The name of the dice.
   * @param numDice The number of dice to roll.
   * @param numSides The number of sides om the dice.
   * @param args The arguments to the dice roll.
   * @param str The dice roll string..
   */
  public DiceRollDiceExpressionNode(
      String name,
      DiceExpressionNode numDice,
      DiceExpressionNode numSides,
      Collection<DiceRollerArgument> args,
      String str) {
    diceName = name;
    rollerArguments = List.copyOf(args);
    diceString = str;
    numberOfSides = numSides;
    numberOfDice = numDice;
  }

  @Override
  public DiceExprResult evaluate(DiceExpressionSymbolTable symbolTable)
      throws UnsupportedOperationException {
    int dice =
        numberOfDice
            .evaluate(symbolTable)
            .getIntResult()
            .orElseThrow(() -> new IllegalArgumentException("Number of dice is missing."));
    int sides =
        numberOfSides
            .evaluate(symbolTable)
            .getIntResult()
            .orElseThrow(() -> new IllegalArgumentException("Number of sides is missing."));
    rollerArguments.forEach(a -> a.evaluate(symbolTable));

    result =
        DiceRollers.getInstance()
            .getDiceRoller(diceName)
            .roll(diceName, dice, sides, rollerArguments);
    return result;
  }

  @Override
  public DiceExprResult getExprResult() {
    return result;
  }

  @Override
  public Collection<DiceExpressionNode> getChildren() {
    return Arrays.asList(numberOfDice, numberOfSides);
  }

  public String getDiceString() {
    return diceString;
  }
}
