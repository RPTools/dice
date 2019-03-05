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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.rptools.dice.DiceExprParser.AssignmentContext;
import net.rptools.dice.DiceExprParser.BinaryExprContext;
import net.rptools.dice.DiceExprParser.BraceGroupContext;
import net.rptools.dice.DiceExprParser.DargDoubleContext;
import net.rptools.dice.DiceExprParser.DargIdentifierContext;
import net.rptools.dice.DiceExprParser.DargIntegerContext;
import net.rptools.dice.DiceExprParser.DargStringContext;
import net.rptools.dice.DiceExprParser.DargVariableContext;
import net.rptools.dice.DiceExprParser.DiceContext;
import net.rptools.dice.DiceExprParser.DiceExprTopLevelContext;
import net.rptools.dice.DiceExprParser.DiceRollsContext;
import net.rptools.dice.DiceExprParser.DiceSidesContext;
import net.rptools.dice.DiceExprParser.DoubleValueContext;
import net.rptools.dice.DiceExprParser.GlobalVariableContext;
import net.rptools.dice.DiceExprParser.InstructionContext;
import net.rptools.dice.DiceExprParser.IntegerValueContext;
import net.rptools.dice.DiceExprParser.LocalVariableContext;
import net.rptools.dice.DiceExprParser.NumDiceContext;
import net.rptools.dice.DiceExprParser.ParenGroupContext;
import net.rptools.dice.DiceExprParser.PropertyVariableContext;
import net.rptools.dice.DiceExprParser.StringContext;
import net.rptools.dice.DiceExprParser.UnaryExprContext;
import net.rptools.dice.expressiontree.AssignmentDiceExpressionNode;
import net.rptools.dice.expressiontree.BinaryDiceExpressionNode;
import net.rptools.dice.expressiontree.DiceExpressionNode;
import net.rptools.dice.expressiontree.DiceRollDiceExpressionNode;
import net.rptools.dice.expressiontree.GroupDiceExpressionNode;
import net.rptools.dice.expressiontree.InstructionDiceExpressionNode;
import net.rptools.dice.expressiontree.ResolveSymbolDiceExpressionNode;
import net.rptools.dice.expressiontree.TopLevelExpressionNode;
import net.rptools.dice.expressiontree.UnaryDiceExpressionNode;
import net.rptools.dice.expressiontree.ValueDiceExpressionNode;
import net.rptools.dice.roller.arguments.DiceRollerArgument;
import net.rptools.dice.DiceExprBaseVisitor;
import net.rptools.dice.symbols.DiceEvalScope;

public class DiceRollVisitor extends DiceExprBaseVisitor<DiceExpressionNode> {

  private final List<DiceExpressionNode> roots = new ArrayList<>();

  private String stripStringQuotes(String str) {
    return str.substring(1, str.length() - 1);
  }

  public List<DiceExpressionNode> getExpressionTrees() {
    return Collections.unmodifiableList(roots);
  }

  @Override
  public DiceExpressionNode visitDiceRolls(DiceRollsContext ctx) {
    ctx.diceExprTopLevel().stream().forEach(de -> roots.add(visit(de)));
    return roots.get(roots.size() - 1);
  }

  @Override
  public DiceExpressionNode visitDoubleValue(DoubleValueContext ctx) {
    return new ValueDiceExpressionNode(Double.parseDouble(ctx.DOUBLE().getText()));
  }

  @Override
  public DiceExpressionNode visitIntegerValue(IntegerValueContext ctx) {
    return new ValueDiceExpressionNode(Integer.parseInt(ctx.INTEGER().getText()));
  }

  @Override
  public DiceExpressionNode visitString(StringContext ctx) {
    return new ValueDiceExpressionNode(stripStringQuotes(ctx.getText()));
  }

  @Override
  public DiceExpressionNode visitParenGroup(ParenGroupContext ctx) {
    return new GroupDiceExpressionNode(GroupDiceExpressionNode.GroupingType.PAREN, visit(ctx.val));
  }

  @Override
  public DiceExpressionNode visitBraceGroup(BraceGroupContext ctx) {
    return new GroupDiceExpressionNode(GroupDiceExpressionNode.GroupingType.BRACE, visit(ctx.val));
  }

  @Override
  public DiceExpressionNode visitBinaryExpr(BinaryExprContext ctx) {
    return new BinaryDiceExpressionNode(ctx.op.getText(), visit(ctx.left), visit(ctx.right));
  }

  @Override
  public DiceExpressionNode visitUnaryExpr(UnaryExprContext ctx) {
    return new UnaryDiceExpressionNode(ctx.op.getText(), visit(ctx.right));
  }

  @Override
  public DiceExpressionNode visitAssignment(AssignmentContext ctx) {
    String variableScopePrefix = ctx.variable().getText().substring(0, 1);
    String variableName = ctx.variable().getText().substring(1);

    return new AssignmentDiceExpressionNode(
        variableName, DiceEvalScope.getScopeForPrefix(variableScopePrefix), visit(ctx.right));
  }

  @Override
  public DiceExpressionNode visitLocalVariable(LocalVariableContext ctx) {
    return new ResolveSymbolDiceExpressionNode(
        ctx.LOCAL_VARIABLE().getText().substring(1), DiceEvalScope.LOCAL);
  }

  @Override
  public DiceExpressionNode visitGlobalVariable(GlobalVariableContext ctx) {
    return new ResolveSymbolDiceExpressionNode(
        ctx.GLOBAL_VARIABLE().getText().substring(1), DiceEvalScope.GLOBAL);
  }

  @Override
  public DiceExpressionNode visitPropertyVariable(PropertyVariableContext ctx) {
    return new ResolveSymbolDiceExpressionNode(
        ctx.PROPERTY_VARIABLE().getText().substring(1), DiceEvalScope.PROPERTY);
  }

  @Override
  public DiceExpressionNode visitDice(DiceContext ctx) {
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
  public DiceExpressionNode visitNumDice(NumDiceContext ctx) {
    if (ctx.integerValue() != null) {
      return new ValueDiceExpressionNode(Integer.parseInt(ctx.getText()));
    } else {
      return visit(ctx.group());
    }
  }

  @Override
  public DiceExpressionNode visitDiceSides(DiceSidesContext ctx) {
    if (ctx.integerValue() != null) {
      return new ValueDiceExpressionNode(Integer.parseInt(ctx.getText()));
    } else {
      return visit(ctx.group());
    }
  }

  @Override
  public DiceExpressionNode visitInstruction(InstructionContext ctx) {
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
  public DiceExpressionNode visitDiceExprTopLevel(DiceExprTopLevelContext ctx) {
    return new TopLevelExpressionNode(ctx.getText(), visit(ctx.getChild(0)));
  }

  @Override
  public DiceExpressionNode visitDargIdentifier(DargIdentifierContext ctx) {
    return null;
  }

  @Override
  public DiceExpressionNode visitDargVariable(DargVariableContext ctx) {
    return visit(ctx.variable());
  }

  @Override
  public DiceExpressionNode visitDargString(DargStringContext ctx) {
    return new ValueDiceExpressionNode(stripStringQuotes(ctx.getText()));
  }

  @Override
  public DiceExpressionNode visitDargInteger(DargIntegerContext ctx) {
    return visit(ctx.integerValue());
  }

  @Override
  public DiceExpressionNode visitDargDouble(DargDoubleContext ctx) {
    return visit(ctx.doubleValue());
  }
}
