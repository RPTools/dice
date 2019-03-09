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
import net.rptools.dice.DiceExprBaseVisitor;
import net.rptools.dice.DiceExprParser;
import net.rptools.dice.DiceExprParser.DargCriticalFailureContext;
import net.rptools.dice.DiceExprParser.DargCriticalSuccessContext;
import net.rptools.dice.DiceExprParser.DargFailContext;
import net.rptools.dice.DiceExprParser.DargSuccessContext;
import net.rptools.dice.DiceExprParser.DiceArgumentListContext;
import net.rptools.dice.DiceExprParser.DiceArgumentValContext;
import net.rptools.dice.DiceExprParser.DiceArgumentsContext;
import net.rptools.dice.expressiontree.DiceExpressionNode;
import net.rptools.dice.roller.arguments.CriticalDiceRollerArgument;
import net.rptools.dice.roller.arguments.DiceRollerArgument;
import net.rptools.dice.roller.arguments.FailDiceRollerArgument;
import net.rptools.dice.roller.arguments.FumbleDiceRollerArgument;
import net.rptools.dice.roller.arguments.KeepRollerArgument;
import net.rptools.dice.roller.arguments.SuccessDiceRollerArgument;

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

  private DiceExpressionNode extractValue(DiceArgumentValContext ctx) {
    return ctx == null ? null : diceRollVisitor.visit(ctx);
  }

  @Override
  public List<DiceRollerArgument> visitDiceArguments(DiceArgumentsContext ctx) {
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
  public List<DiceRollerArgument> visitDargSuccess(DargSuccessContext ctx) {
    return Collections.singletonList(
        new SuccessDiceRollerArgument(
            extractValue(ctx.val), ctx.op == null ? null : ctx.op.getText()
        )
    );
  }

  @Override
  public List<DiceRollerArgument> visitDargFail(DargFailContext ctx) {
    return Collections.singletonList(
        new FailDiceRollerArgument(
            extractValue(ctx.val), ctx.op == null ? null : ctx.op.getText()
        )
    );
  }
  @Override
  public List<DiceRollerArgument> visitDargKeepHighest(DiceExprParser.DargKeepHighestContext ctx) {
    return Collections.singletonList(
        KeepRollerArgument.keepHighest(extractValue(ctx.val))
    );
  }

  @Override
  public List<DiceRollerArgument> visitDargKeepLowest(DiceExprParser.DargKeepLowestContext ctx) {
    return Collections.singletonList(
        KeepRollerArgument.keepLowest(extractValue(ctx.val))
    );
  }

  // TODO HERE!!!!
  @Override
  public List<DiceRollerArgument> visitDargDropHighest(DiceExprParser.DargDropHighestContext ctx) {
    return Collections.singletonList(
        KeepRollerArgument.dropHighest(extractValue(ctx.val))
    );
  }

  @Override
  public List<DiceRollerArgument> visitDargDropLowest(DiceExprParser.DargDropLowestContext ctx) {
    return Collections.singletonList(
        KeepRollerArgument.dropLowest(extractValue(ctx.val))
    );
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
  */

  /*


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
      DargCriticalSuccessContext ctx) {
    return Collections.singletonList(
        new CriticalDiceRollerArgument(
            extractValue(ctx.diceArgumentVal()), ctx.op == null ? null : ctx.op.getText()));
  }

  @Override
  public List<DiceRollerArgument> visitDargCriticalFailure(
      DargCriticalFailureContext ctx) {
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
      DiceArgumentListContext ctx) {
    return ctx.diceArgument().stream()
        .map(arg -> arg.accept(this))
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }
}
