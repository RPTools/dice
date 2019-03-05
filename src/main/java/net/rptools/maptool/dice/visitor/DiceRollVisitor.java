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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.rptools.maptool.dice.DiceExprBaseVisitor;
import net.rptools.maptool.dice.DiceExprParser;
import net.rptools.maptool.dice.InstructionArgumentVisitor;
import net.rptools.maptool.dice.expressiontree.*;
import net.rptools.maptool.dice.roller.arguments.DiceRollerArgument;
import net.rptools.maptool.dice.symbols.DiceEvalScope;

public class DiceRollVisitor extends DiceExprBaseVisitor<DiceExpressionNode> {

  private final List<DiceExpressionNode> roots = new ArrayList<>();

  private String stripStringQuotes(String str) {
    return str.substring(1, str.length() - 1);
  }

  public List<DiceExpressionNode> getExpressionTrees() {
    return Collections.unmodifiableList(roots);
  }

  @Override
  public DiceExpressionNode visitDiceRolls(DiceExprParser.DiceRollsContext ctx) {
    ctx.diceExprTopLevel().stream().forEach(de -> roots.add(visit(de)));
    return roots.get(roots.size() - 1);
  }

  @Override
  public DiceExpressionNode visitDoubleValue(DiceExprParser.DoubleValueContext ctx) {
    return new ValueDiceExpressionNode(Double.parseDouble(ctx.DOUBLE().getText()));
  }

  @Override
  public DiceExpressionNode visitIntegerValue(DiceExprParser.IntegerValueContext ctx) {
    return new ValueDiceExpressionNode(Integer.parseInt(ctx.INTEGER().getText()));
  }

  @Override
  public DiceExpressionNode visitString(DiceExprParser.StringContext ctx) {
    return new ValueDiceExpressionNode(stripStringQuotes(ctx.getText()));
  }

  @Override
  public DiceExpressionNode visitParenGroup(DiceExprParser.ParenGroupContext ctx) {
    return new GroupDiceExpressionNode(GroupDiceExpressionNode.GroupingType.PAREN, visit(ctx.val));
  }

  @Override
  public DiceExpressionNode visitBraceGroup(DiceExprParser.BraceGroupContext ctx) {
    return new GroupDiceExpressionNode(GroupDiceExpressionNode.GroupingType.BRACE, visit(ctx.val));
  }

  @Override
  public DiceExpressionNode visitBinaryExpr(DiceExprParser.BinaryExprContext ctx) {
    return new BinaryDiceExpressionNode(ctx.op.getText(), visit(ctx.left), visit(ctx.right));
  }

  @Override
  public DiceExpressionNode visitUnaryExpr(DiceExprParser.UnaryExprContext ctx) {
    return new UnaryDiceExpressionNode(ctx.op.getText(), visit(ctx.right));
  }

  @Override
  public DiceExpressionNode visitAssignment(DiceExprParser.AssignmentContext ctx) {
    String variableScopePrefix = ctx.variable().getText().substring(0, 1);
    String variableName = ctx.variable().getText().substring(1);

    return new AssignmentDiceExpressionNode(
        variableName, DiceEvalScope.getScopeForPrefix(variableScopePrefix), visit(ctx.right));
  }

  @Override
  public DiceExpressionNode visitLocalVariable(DiceExprParser.LocalVariableContext ctx) {
    return new ResolveSymbolDiceExpressionNode(
        ctx.LOCAL_VARIABLE().getText().substring(1), DiceEvalScope.LOCAL);
  }

  @Override
  public DiceExpressionNode visitGlobalVariable(DiceExprParser.GlobalVariableContext ctx) {
    return new ResolveSymbolDiceExpressionNode(
        ctx.GLOBAL_VARIABLE().getText().substring(1), DiceEvalScope.GLOBAL);
  }

  @Override
  public DiceExpressionNode visitPropertyVariable(DiceExprParser.PropertyVariableContext ctx) {
    return new ResolveSymbolDiceExpressionNode(
        ctx.PROPERTY_VARIABLE().getText().substring(1), DiceEvalScope.PROPERTY);
  }

  @Override
  public DiceExpressionNode visitDice(DiceExprParser.DiceContext ctx) {
    DiceExpressionNode numDice;
    if (ctx.numDice() == null) {
      numDice = new ValueDiceExpressionNode(1);
    } else {
      numDice = visit(ctx.numDice());
    }

    String roll = ctx.getText();

    final DiceExpressionNode sides = visit(ctx.diceSides());
    final String name = ctx.diceName().getText();

    List<DiceRollerArgument> argList;
    if (ctx.diceArguments() != null) {
      final var diceArgVisitor = new DiceArgumentVisitor(this);
      argList = diceArgVisitor.visit(ctx.diceArguments());
    } else {
      argList = Collections.emptyList();
    }

    return new DiceRollDiceExpressionNode(name, numDice, sides, argList, ctx.getText());
  }

  @Override
  public DiceExpressionNode visitNumDice(DiceExprParser.NumDiceContext ctx) {
    if (ctx.integerValue() != null) {
      return new ValueDiceExpressionNode(Integer.parseInt(ctx.getText()));
    } else {
      return visit(ctx.group());
    }
  }

  @Override
  public DiceExpressionNode visitDiceSides(DiceExprParser.DiceSidesContext ctx) {
    if (ctx.integerValue() != null) {
      return new ValueDiceExpressionNode(Integer.parseInt(ctx.getText()));
    } else {
      return visit(ctx.group());
    }
  }

  @Override
  public DiceExpressionNode visitInstruction(DiceExprParser.InstructionContext ctx) {
    String instrName = ctx.instructionName.getText();

    List<String> argList;
    if (ctx.instructionArgumentList() != null) {
      final var instructionArgVisitor = new InstructionArgumentVisitor();
      argList = instructionArgVisitor.visit(ctx.instructionArgumentList());
    } else {
      argList = Collections.emptyList();
    }

    return new InstructionDiceExpressionNode(instrName, argList);
  }

  @Override
  public DiceExpressionNode visitDiceExprTopLevel(DiceExprParser.DiceExprTopLevelContext ctx) {
    return new TopLevelExpressionNode(ctx.getText(), visit(ctx.getChild(0)));
  }

  @Override
  public DiceExpressionNode visitDargIdentifier(DiceExprParser.DargIdentifierContext ctx) {
    return null;
  }

  @Override
  public DiceExpressionNode visitDargVariable(DiceExprParser.DargVariableContext ctx) {
    return visit(ctx.variable());
  }

  @Override
  public DiceExpressionNode visitDargString(DiceExprParser.DargStringContext ctx) {
    return new ValueDiceExpressionNode(stripStringQuotes(ctx.getText()));
  }

  @Override
  public DiceExpressionNode visitDargInteger(DiceExprParser.DargIntegerContext ctx) {
    return visit(ctx.integerValue());
  }

  @Override
  public DiceExpressionNode visitDargDouble(DiceExprParser.DargDoubleContext ctx) {
    return visit(ctx.doubleValue());
  }
}
