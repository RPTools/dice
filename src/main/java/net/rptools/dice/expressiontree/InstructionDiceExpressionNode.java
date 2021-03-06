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
import java.util.List;
import net.rptools.dice.result.DiceExprResult;
import net.rptools.dice.symbols.DiceExpressionSymbolTable;

public class InstructionDiceExpressionNode implements DiceExpressionNode {

  private final String instructionName;

  private final List<String> arguments;

  public InstructionDiceExpressionNode(String name, Collection<String> args) {
    instructionName = name;
    arguments = List.copyOf(args);
  }

  @Override
  public DiceExprResult evaluate(DiceExpressionSymbolTable symbolTable)
      throws UnsupportedOperationException {
    return null;
  }

  @Override
  public DiceExprResult getExprResult() {
    return null;
  }

  @Override
  public Collection<DiceExpressionNode> getChildren() {
    return Collections.emptyList();
  }

  String getInstructionName() {
    return instructionName;
  }

  List<String> getArguments() {
    return arguments;
  }
}
