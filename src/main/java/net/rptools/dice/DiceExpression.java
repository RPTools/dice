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
package net.rptools.dice;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import net.rptools.dice.expressiontree.ResultDetails;
import net.rptools.dice.result.ResultFormatter;
import net.rptools.dice.expressiontree.DiceExpressionNode;
import net.rptools.dice.symbols.DiceExpressionSymbolTable;
import net.rptools.dice.visitor.DiceRollVisitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class DiceExpression {

  private final DiceRollVisitor visitor;
  private final List<DiceExpressionNode> roots;

  private DiceExpression(CharStream charStream) {
    // Create a lexer that feeds off of input CharStream
    DiceExprLexer lexer = new DiceExprLexer(charStream);

    // create a buffer of tokens pulled from the lexer
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    // create a parser that feeds off the tokens buffer
    DiceExprParser parser = new DiceExprParser(tokens);

    ParseTree parseTree = parser.diceRolls();

    // Create a generic parse expressiontree walker that can trigger callbacks
    ParseTreeWalker walker = new ParseTreeWalker();

    visitor = new DiceRollVisitor();
    visitor.visit(parseTree);

    roots = visitor.getExpressionTrees();
  }

  public static DiceExpression fromString(String expr) {
    return new DiceExpression(CharStreams.fromString(expr));
  }

  public static DiceExpression fromFile(String filename) throws IOException {
    return new DiceExpression(CharStreams.fromFileName(filename));
  }

  public static DiceExpression fromInputStream(InputStream inputStream) throws IOException {
    return new DiceExpression(CharStreams.fromStream(inputStream));
  }

  public void execute(DiceExpressionSymbolTable symbolTable) {
    for (var root : roots) {
      root.evaluate(symbolTable);
    }
  }

  public Optional<String> format(ResultFormatter formatter) {
    var results = new ResultDetails();
    return results.format(roots, formatter);
  }
}
