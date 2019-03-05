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
package net.rptools.maptool.dice.visitor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import net.rptools.maptool.dice.DiceExprBaseVisitor;
import net.rptools.maptool.dice.DiceExprParser;
import net.rptools.maptool.dice.expressiontree.DiceExpressionNode;
import net.rptools.maptool.dice.roller.arguments.*;

/** Visitor used to visit dice expression arguments in the parse expressiontree. */
public class DiceArgumentVisitor extends DiceExprBaseVisitor<List<DiceRollerArgument>> {

  private final DiceRollVisitor diceRollVisitor;

  DiceArgumentVisitor(DiceRollVisitor from) {
    diceRollVisitor = from;
  }

  /*@Override
  public List<DiceRollerArgumentHolder> visitDiceArgument(DiceExprParser.DiceArgumentContext ctx) {
      String name = ctx.name.getText();
      String op = ctx.op != null ? ctx.op.getText() : null;

      List<DiceRollerArgumentHolder> args = new ArrayList<>();

      if (ctx.val == null) {
          args.add(new DiceRollerArgumentHolder(name));
      } else {
          DiceExpressionNode arg = diceRollVisitor.visit(ctx.diceArgumentVal());
          args.add(new DiceRollerArgumentHolder(name, op, arg));
      }

      return args;
  }*/

  private DiceExpressionNode extractValue(DiceExprParser.DiceArgumentValContext ctx) {
    return ctx == null ? null : diceRollVisitor.visit(ctx);
  }

  @Override
  public List<DiceRollerArgument> visitDiceArguments(DiceExprParser.DiceArgumentsContext ctx) {
    return super.visitDiceArguments(ctx);
  }

  /*
  @Override
  public List<DiceRollerArgument> visitDargAdd(DiceExprParser.DargAddContext ctx) {
      return Collections.singletonList(new AddDiceRollerArgument(extractValue(ctx.diceArgumentVal())));
  }

  @Override
  public List<DiceRollerArgument> visitDargSubtract(DiceExprParser.DargSubtractContext ctx) {
      return super.visitDargSubtract(ctx); // TODO
  }

  @Override
  public List<DiceRollerArgument> visitDargSubtractMin0(DiceExprParser.DargSubtractMin0Context ctx) {
      return super.visitDargSubtractMin0(ctx); // TODO
  }

  @Override
  public List<DiceRollerArgument> visitDargSubtractNoMin(DiceExprParser.DargSubtractNoMinContext ctx) {
      return super.visitDargSubtractNoMin(ctx); // TODO
  }

  */

  @Override
  public List<DiceRollerArgument> visitDargSuccess(DiceExprParser.DargSuccessContext ctx) {
    if (ctx.val != null) {
      System.out.println("HERE:   " + ctx.op + " " + ctx.val.getText());
    } else {
      System.out.println("HERE:   NULL");
    }
    return Collections.singletonList(
        new SuccessDiceRollerArgument(
            extractValue(ctx.val), ctx.op == null ? null : ctx.op.getText()));
  }

  @Override
  public List<DiceRollerArgument> visitDargFail(DiceExprParser.DargFailContext ctx) {
    return Collections.singletonList(
        new FailDiceRollerArgument(
            extractValue(ctx.val), ctx.op == null ? null : ctx.op.getText()));
  }

  /*
  @Override
  public List<DiceRollerArgument> visitDargReroll(DiceExprParser.DargRerollContext ctx) {
      return super.visitDargReroll(ctx); // TODO
  }

  @Override
  public List<DiceRollerArgument> visitDargRerollOnce(DiceExprParser.DargRerollOnceContext ctx) {
      return super.visitDargRerollOnce(ctx); // TODO
  }

  @Override
  public List<DiceRollerArgument> visitDargKeep(DiceExprParser.DargKeepContext ctx) {
      return super.visitDargKeep(ctx); // TODO
  }

  @Override
  public List<DiceRollerArgument> visitDargDrop(DiceExprParser.DargDropContext ctx) {
      return super.visitDargDrop(ctx); // TODO
  }

  @Override
  public List<DiceRollerArgument> visitDargKeepHighest(DiceExprParser.DargKeepHighestContext ctx) {
      return super.visitDargKeepHighest(ctx); // TODO
  }

  @Override
  public List<DiceRollerArgument> visitDargKeepLowest(DiceExprParser.DargKeepLowestContext ctx) {
      return super.visitDargKeepLowest(ctx); // TODO
  }

  @Override
  public List<DiceRollerArgument> visitDargDropHighest(DiceExprParser.DargDropHighestContext ctx) {
      return super.visitDargDropHighest(ctx); // TODO
  }

  @Override
  public List<DiceRollerArgument> visitDargDropLowest(DiceExprParser.DargDropLowestContext ctx) {
      return super.visitDargDropLowest(ctx); // TODO
  }

  @Override
  public List<DiceRollerArgument> visitDargExplode(DiceExprParser.DargExplodeContext ctx) {
      return super.visitDargExplode(ctx); // TODO
  }

  @Override
  public List<DiceRollerArgument> visitDargExplodeOnce(DiceExprParser.DargExplodeOnceContext ctx) {
      return super.visitDargExplodeOnce(ctx); // TODO
  }

  @Override
  public List<DiceRollerArgument> visitDargCompoundExploding(DiceExprParser.DargCompoundExplodingContext ctx) {
      return super.visitDargCompoundExploding(ctx); // TODO
  }
  */

  @Override
  public List<DiceRollerArgument> visitDargCriticalSuccess(
      DiceExprParser.DargCriticalSuccessContext ctx) {
    return Collections.singletonList(
        new CriticalDiceRollerArgument(
            extractValue(ctx.diceArgumentVal()), ctx.op == null ? null : ctx.op.getText()));
  }

  @Override
  public List<DiceRollerArgument> visitDargCriticalFailure(
      DiceExprParser.DargCriticalFailureContext ctx) {
    return Collections.singletonList(
        new FumbleDiceRollerArgument(
            extractValue(ctx.diceArgumentVal()), ctx.op == null ? null : ctx.op.getText()));
  }

  /*
  @Override
  public List<DiceRollerArgument> visitDargAscendingOrder(DiceExprParser.DargAscendingOrderContext ctx) {
      return super.visitDargAscendingOrder(ctx); // TODO
  }

  @Override
  public List<DiceRollerArgument> visitDargDescendingOrde(DiceExprParser.DargDescendingOrdeContext ctx) {
      return super.visitDargDescendingOrde(ctx); // TODO
  }

  @Override
  public List<DiceRollerArgument> visitDargFormat(DiceExprParser.DargFormatContext ctx) {
      return super.visitDargFormat(ctx); // TODO
  }
  */

  @Override
  public List<DiceRollerArgument> visitDiceArgumentList(
      DiceExprParser.DiceArgumentListContext ctx) {
    return ctx.diceArgument().stream()
        .map(arg -> arg.accept(this))
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }
}
