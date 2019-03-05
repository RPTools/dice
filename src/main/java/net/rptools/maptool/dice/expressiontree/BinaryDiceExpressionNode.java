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
package net.rptools.maptool.dice.expressiontree;

import java.util.Arrays;
import java.util.Collection;
import net.rptools.maptool.dice.result.DiceExprResult;
import net.rptools.maptool.dice.symbols.DiceExpressionSymbolTable;

/** Node that represents a binary operation in the dice roll script. */
public class BinaryDiceExpressionNode implements DiceExpressionNode {

  /** The operator that this represents. */
  private final String operator;

  /** The result of the expression. */
  private DiceExprResult diceExprResult;

  /** The node representing what is on the left of the operator. */
  private final DiceExpressionNode left;

  /** The node representing what is on the right of the operator. */
  private final DiceExpressionNode right;

  /**
   * Creates a new node for a binary operation.
   *
   * @param op The binary operator to represent.
   * @param left What is to the left of the operator.
   * @param right What is to the right of the operator.
   */
  public BinaryDiceExpressionNode(String op, DiceExpressionNode left, DiceExpressionNode right) {
    operator = op;
    this.left = left;
    this.right = right;
  }

  @Override
  public DiceExprResult getExprResult() {
    return diceExprResult;
  }

  @Override
  public Collection<DiceExpressionNode> getChildren() {
    return Arrays.asList(left, right);
  }

  @Override
  public DiceExprResult evaluate(DiceExpressionSymbolTable symbolTable)
      throws UnsupportedOperationException {
    switch (operator) {
      case "+":
        diceExprResult =
            DiceExprResult.add(left.evaluate(symbolTable), right.evaluate(symbolTable));
        break;
      case "-":
        diceExprResult =
            DiceExprResult.subtract(left.evaluate(symbolTable), right.evaluate(symbolTable));
        break;
      case "*":
        diceExprResult =
            DiceExprResult.multiply(left.evaluate(symbolTable), right.evaluate(symbolTable));
        break;
      case "/":
        diceExprResult =
            DiceExprResult.divide(left.evaluate(symbolTable), right.evaluate(symbolTable));
        break;
      default:
        throw new UnsupportedOperationException("Unknown binary operator: " + operator);
    }
    return diceExprResult;
  }
}
