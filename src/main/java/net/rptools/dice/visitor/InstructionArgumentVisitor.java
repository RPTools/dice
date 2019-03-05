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

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import net.rptools.dice.DiceExprParser.DiceArgumentListContext;
import net.rptools.dice.DiceExprParser.InstructionArgumentContext;
import net.rptools.dice.DiceExprParser.InstructionArgumentListContext;
import net.rptools.dice.DiceExprBaseVisitor;

public class InstructionArgumentVisitor extends DiceExprBaseVisitor<List<String>> {

  @Override
  public List<String> visitInstructionArgumentList(
      InstructionArgumentListContext ctx) {
    return ctx.instructionArgument().stream()
        .map(arg -> arg.accept(this))
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

  @Override
  public List<String> visitInstructionArgument(InstructionArgumentContext ctx) {
    return Collections.singletonList(ctx.getChild(0).getText());
  }

  @Override
  public List<String> visitDiceArgumentList(DiceArgumentListContext ctx) {
    return ctx.diceArgument().stream()
        .map(arg -> arg.accept(this))
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }
}
