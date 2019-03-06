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
package net.rptools.dice.result;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/** This class implements a plain text */
public class HTMLResultFormatter implements ResultFormatter {

  /** Class used to keep track of the output for each of the expressions. */
  private static class Details {
    /** The detailed information in the output. */
    private final List<String> details;
    /** The total result of the output. */
    private final String result;
    /** The expression that the output is for. */
    private final String expression;

    /**
     * Creates a new <code>Details</code> object to hold the output details generated.
     *
     * @param res The total result.
     * @param det The detailed information in the output.
     * @param expr The expression that the output is for.
     */
    Details(String res, Collection<String> det, String expr) {
      result = res;
      details = List.copyOf(det);
      expression = expr;
    }

    /**
     * Returns the total result of the expression as a <code>String</code>.
     *
     * @return the total result of the expression as a <code>String</code>.
     */
    public String getResult() {
      return result;
    }

    /**
     * Returns a list of the lines of detailed information in the output.
     *
     * @return a list of the lines of detailed information in the output.
     */
    List<String> getDetails() {
      return details;
    }

    /**
     * Returns the expression that was used to generate the output.
     *
     * @return the expression that was used to generate the output.
     */
    public String getExpression() {
      return expression;
    }
  }

  /** The details for the current expression. */
  private List<String> currentDetails = new LinkedList<>();
  /** The result of the current expression. */
  private String currentResult = "";
  /** The current expression that the output is being built for. */
  private String currentExpression = "";

  /** A list of all the details captured for each of the completed expressions. */
  private final List<Details> details = new LinkedList<>();

  /** Should the output be hidden. */
  private boolean hidden = false;

  @Override
  public void setResult(DiceExprResult result) {
    currentResult = result.getStringResult();
  }

  @Override
  public void setExpression(String expression) {
    currentExpression = expression;
  }

  @Override
  public void addResolveSymbol(String symbol, DiceExprResult value) {
    currentDetails.add(value.getStringResult() + " <- " + symbol);
  }

  @Override
  public void addAssignSymbol(String symbol, DiceExprResult value) {
    currentDetails.add("set " + symbol + " = " + value.getStringResult());
  }

  @Override
  public void addPromptValue(String prompt, DiceExprResult value) {
    currentDetails.add("input (" + prompt + ") = " + value.getStringResult());
  }

  @Override
  public void addRoll(DiceRolls rolls) {
    StringBuilder sb = new StringBuilder();

    sb.append(rolls.getNumberOfRolls()).append(rolls.getName()).append(rolls.getNumberOfSides());
    sb.append(" = [");
    String listOfRolls =
        rolls.getDiceRolls().stream()
            .map(
                r -> {
                  String postfix = "";
                  if (r.isSuccess()) {
                    postfix += "(s)";
                  }
                  if (r.isFailure()) {
                    postfix += "(f)";
                  }
                  if (r.isFumble()) {
                    postfix += "(F)";
                  }
                  if (r.isCritical()) {
                    postfix += "(C)";
                  }
                  return r.getValue() + postfix;
                })
            .collect(Collectors.joining(", "));

    sb.append(listOfRolls).append("] = ").append(rolls.getResult().getStringResult());

    currentDetails.add(sb.toString());
  }

  /**
   * This method formats a {@link Details} object as a <code>String</code>.
   *
   * @param details The object to format.
   * @return the object formatted as a <code>String</code>.
   */
  private Optional<String> format(Details details) {
    StringBuilder sb = new StringBuilder();

    if (details.getResult() == null || details.getResult().length() == 0) {
      return Optional.empty();
    }

    sb.append(details.getExpression()).append(" = ").append(details.getResult()).append("\n");

    details.getDetails().forEach(l -> sb.append("\t").append(l).append("\n"));

    return Optional.of(sb.toString());
  }

  @Override
  public Optional<String> format() {
    StringBuilder sb = new StringBuilder();
    boolean first = true;
    for (var det : details) {
      if (!first) {
        sb.append("<br/>");
      } else {
        first = false;
      }
      var str = format(det);
      str.ifPresent(sb::append);
    }

    if (sb.length() > 0) {
      return Optional.of(sb.toString());
    } else {
      return Optional.empty();
    }
  }

  @Override
  public void hideOutput() {
    hidden = true;
  }

  @Override
  public void showOutput() {
    hidden = false;
  }

  @Override
  public boolean isOutputHidden() {
    return hidden;
  }

  @Override
  public void start() {
    currentDetails = new LinkedList<>();
    currentResult = "";
  }

  @Override
  public void end() {
    details.add(new Details(currentResult, currentDetails, currentExpression));
  }
}
