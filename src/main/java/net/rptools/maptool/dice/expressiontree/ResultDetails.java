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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import net.rptools.maptool.dice.result.ResultFormatter;

public class ResultDetails {

  public Optional<String> format(Collection<DiceExpressionNode> roots, ResultFormatter formatter) {
    roots.forEach(r -> format(r, formatter));

    return formatter.format();
  }

  private void format(DiceExpressionNode root, ResultFormatter formatter) {

    formatter.start();

    List<DiceExpressionNode> nodeList = new ArrayList<>();

    visitNode(root, nodeList);

    formatter.setResult(root.getExprResult());

    for (var node : nodeList) {
      if (node instanceof AssignmentDiceExpressionNode) {
        var aNode = (AssignmentDiceExpressionNode) node;
        formatter.addAssignSymbol(aNode.getVariableName(), aNode.getExprResult());
      } else if (node instanceof DiceRollDiceExpressionNode) {
        formatter.addRoll(node.getExprResult().getDiceRolls());
      } else if (node instanceof InstructionDiceExpressionNode) {
        var iNode = (InstructionDiceExpressionNode) node;
        if ("show".equalsIgnoreCase(iNode.getInstructionName())) {
          formatter.showOutput();
        } else if ("hide".equalsIgnoreCase(iNode.getInstructionName())) {
          formatter.hideOutput();
        }
      } else if (node instanceof ResolveSymbolDiceExpressionNode) {
        var rNode = (ResolveSymbolDiceExpressionNode) node;
        formatter.addResolveSymbol(rNode.getVariableName(), rNode.getExprResult());
      } else if (node instanceof TopLevelExpressionNode) {
        var tNode = (TopLevelExpressionNode) node;
        formatter.setExpression(tNode.getExpression());
      }
    }

    formatter.end();
  }

  private void visitNode(DiceExpressionNode root, List<DiceExpressionNode> nodeList) {
    root.getChildren().forEach(r -> visitNode(r, nodeList));
    nodeList.add(root);
  }
}
