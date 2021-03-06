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

import java.util.Collection;
import java.util.Collections;
import net.rptools.dice.result.DiceExprResult;
import net.rptools.dice.symbols.DiceExpressionSymbolTable;

/** Node that represents a unary operation in the dice roll script. */
public class UnaryDiceExpressionNode implements DiceExpressionNode {

  /** The operator that this represents. */
  private final String operator;

  /** The result of the expression. */
  private DiceExprResult diceExprResult;

  /** The node representing the operand. */
  private final DiceExpressionNode operand;

  /**
   * Creates a new node for a unary operation.
   *
   * @param op The unary operator to represent.
   * @param operand The operand for this operator.
   */
  public UnaryDiceExpressionNode(String op, DiceExpressionNode operand) {
    operator = op;
    this.operand = operand;
  }

  @Override
  public DiceExprResult getExprResult() {
    return diceExprResult;
  }

  @Override
  public Collection<DiceExpressionNode> getChildren() {
    return Collections.singletonList(operand);
  }

  public DiceExprResult evaluate(DiceExpressionSymbolTable symbolTable)
      throws UnsupportedOperationException {
    switch (operator) {
      case "-":
        diceExprResult = DiceExprResult.negate(operand.evaluate(symbolTable));
        return diceExprResult;
      default:
        throw new UnsupportedOperationException("Unknown unary operator: " + operator);
    }
  }
}
