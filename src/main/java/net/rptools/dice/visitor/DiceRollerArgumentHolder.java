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
package net.rptools.dice.visitor;

import java.util.Optional;
import net.rptools.dice.expressiontree.DiceExpressionNode;

public class DiceRollerArgumentHolder {
  private final String name;
  private final String operator;
  private final DiceExpressionNode value;

  public DiceRollerArgumentHolder(String name, String operator, DiceExpressionNode value) {
    this.name = name;
    this.operator = operator;
    this.value = value;
  }

  public DiceRollerArgumentHolder(String name) {
    this.name = name;
    this.operator = null;
    this.value = null;
  }

  public String getName() {
    return name;
  }

  public Optional<String> getOperator() {
    return Optional.ofNullable(operator);
  }

  public Optional<DiceExpressionNode> getValue() {
    return Optional.ofNullable(value);
  }
}
