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
package net.rptools.dice.result.html;

//import com.mitchellbosecke.pebble.PebbleEngine;
//import com.mitchellbosecke.pebble.loader.ClasspathLoader;
//import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.rptools.dice.result.DiceExprResult;
import net.rptools.dice.result.DiceRolls;
import net.rptools.dice.result.ResultFormatter;

/** This class implements a plain text */
public class HTMLResultFormatter implements ResultFormatter {


  //private final PebbleEngine pebbleEngine;
  //private final PebbleTemplate inlineDiceRollTemplate;
  private Template template;
  private final List<Result> results = new LinkedList<>();
  private Result workingResult = new Result();


  public HTMLResultFormatter() {
    //ClasspathLoader loader = new ClasspathLoader();
    //loader.setSuffix(".peb");
    //loader.setPrefix("net/rptools/dice/templates");

    //pebbleEngine = new PebbleEngine.Builder().loader(loader).build();
    //inlineDiceRollTemplate = pebbleEngine.getTemplate("InlineDiceRollTemplate");

    TemplateLoader loader = new ClassPathTemplateLoader();
    loader.setPrefix("/net/rptools/dice/templates");
    loader.setSuffix(".hbs");
    Handlebars handlebars = new Handlebars(loader);
    handlebars.prettyPrint(true);

    try {
      template = handlebars.compile("InlineDiceRollTemplate");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void setResult(DiceExprResult result) {
    workingResult.setResult(result);
  }

  @Override
  public void setExpression(String expression) {
    workingResult.setExpression(expression);
  }

  @Override
  public void addResolveSymbol(String symbol, DiceExprResult value) {
    workingResult.addDetail(Detail.createVariableResove(symbol, value));
  }

  @Override
  public void addAssignSymbol(String symbol, DiceExprResult value) {
    workingResult.addDetail(Detail.createVariableResove(symbol, value));
  }

  @Override
  public void addPromptValue(String prompt, DiceExprResult value) {
    workingResult.addDetail(Detail.createPrompt(prompt, value));
  }

  @Override
  public void addRoll(DiceRolls rolls) {
    workingResult.addDetail(Detail.createDiceRoll(rolls));
  }

  @Override
  public Optional<String> format() {
    /*StringWriter stringWriter = new StringWriter();

    try {
      inlineDiceRollTemplate.evaluate(stringWriter, Collections.singletonMap("diceResults", results));
    } catch (IOException e) {
      e.printStackTrace();
    }

    return Optional.of(stringWriter.toString());
    */
    try {
      return Optional.of(template.apply(Collections.singletonMap("diceResults", results)));
    } catch (IOException e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

  @Override
  public void hideOutput() {
    workingResult.setHidden(true);
  }

  @Override
  public void showOutput() {
    workingResult.setHidden(false);
  }

  @Override
  public boolean isOutputHidden() {
    return workingResult.isHidden();
  }

  @Override
  public void start() {
    boolean hidden = isOutputHidden();

    workingResult = new Result();
    workingResult.setHidden(hidden);
  }

  @Override
  public void end() {
    results.add(workingResult);
  }
}
