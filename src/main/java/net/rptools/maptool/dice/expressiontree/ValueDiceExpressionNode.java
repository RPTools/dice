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

import java.util.Collection;
import java.util.Collections;
import net.rptools.maptool.dice.result.DiceExprResult;
import net.rptools.maptool.dice.symbols.DiceExpressionSymbolTable;

/** Note which represents a value expression */
public class ValueDiceExpressionNode implements DiceExpressionNode {

  /** The value that this object represents. */
  private final DiceExprResult value;

  /**
   * Creates an integer value.
   *
   * @param value the integer.
   */
  public ValueDiceExpressionNode(int value) {
    this.value = DiceExprResult.getIntResult(value);
  }

  /**
   * Creates a double value.
   *
   * @param value the doulbe.
   */
  public ValueDiceExpressionNode(double value) {
    this.value = DiceExprResult.getDoubleResult(value);
  }

  /**
   * Creates a String value.
   *
   * @param value the String.
   */
  public ValueDiceExpressionNode(String value) {
    this.value = DiceExprResult.getStringResult(value);
  }

  @Override
  public DiceExprResult evaluate(DiceExpressionSymbolTable symbolTable)
      throws UnsupportedOperationException {
    return value;
  }

  @Override
  public DiceExprResult getExprResult() {
    return value;
  }

  @Override
  public Collection<DiceExpressionNode> getChildren() {
    return Collections.emptyList();
  }
}
