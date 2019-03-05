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
import net.rptools.maptool.dice.symbols.DiceExpressionSymbolTable;

/**
 * This class represents modifier arguments to a dice roll. Arguments are of the form
 * <i>name</i>[<i>operator</i>][<i>number</i>]
 */
public class UnknownDiceRollerArgument implements DiceRollerArgument {

  /** The name of the argument. */
  private final String argumentName;

  /** The operator that forms part of the argument. */
  private final String operator;

  /** The value on the right hand side of the argument. */
  private final DiceExprResult value;

  /** The number of sides for the dice roll. */
  private final int numberSides;

  /**
   * Creates a new dice roller argument.
   *
   * @param name The name of the argument.
   * @param op The operator that is part of the argument.
   * @param val The value of the argument
   * @param sides The number of sides for the dice.
   */
  public UnknownDiceRollerArgument(String name, String op, DiceExprResult val, int sides) {
    argumentName = name;
    operator = op;
    value = val;
    numberSides = sides;
  }

  /**
   * Creates a new dice roller argument.
   *
   * @param name The name of the argument.
   * @param sides The number of sides for the dice.
   */
  public UnknownDiceRollerArgument(String name, int sides) {
    argumentName = name;
    operator = null;
    value = DiceExprResult.UNDEFINED;
    numberSides = sides;
  }

  /**
   * Returns the argument name.
   *
   * @return the name of the argument.
   */
  @Override
  public String getArgumentName() {
    return argumentName;
  }

  /**
   * Returns the operator if any that was used im the argument..
   *
   * @return the operator if any that was used.
   */
  @Override
  public Optional<String> getOperator() {
    return Optional.ofNullable(operator);
  }

  /**
   * Returns the value for the argument.
   *
   * @return the value for the argument.
   */
  @Override
  public DiceExprResult getValue() {
    return value;
  }

  @Override
  public void evaluate(DiceExpressionSymbolTable symbolTable) {
    // TODO
  }
}
