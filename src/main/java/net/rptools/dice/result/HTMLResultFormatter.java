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

  private static final String SPAN_ELEMENT = "span";
  private static final String DIV_ELEMENT = "div";

  private static final String RESOLVE_SYMBOL_CLASS = "resolveSymbol";
  private static final String ASSIGN_SYMBOL_CLASS = "assignSymbol";
  private static final String PROMPT_CLASS = "prompt";
  private static final String INLINE_ROLL_PART_CLASS = "inlineRollPart";
  private static final String INLINE_ROLL_CLASS = "inlineRoll";
  private static final String HIDDEN_CLASS_NAME = "hidden";
  private static final String DICE_EXPRESSION_CLASS = "diceExpression";
  private static final String RESULT_CLASS = "diceResult";
  private static final String DIE_ROLL_CLASS = "dieRoll";
  private static final String SUCCESSFUL_DIE_ROLL_CLASS = "successfulDieRoll";
  private static final String FAILURE_DIE_ROLL_CLASS = "failureDieRoll";
  private static final String CRITICAL_SUCCESS_DIE_ROLL_CLASS = "criticalSuccessDieRoll";
  private static final String CRITICAL_FUMBLE_DIE_ROLL_CLASS = "criticalFumbleDieRile";
  private static final String ROLL_RESULT_CLASS = "rollResult";

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

  private String buildStartOfElement(String elementName, String className, boolean isHidden) {
    StringBuilder sb = new StringBuilder();
    sb.append("<").append(elementName).append(" class=").append('"').append(className);
    if (isHidden) {
      sb.append(" ").append(HIDDEN_CLASS_NAME);
    }
    sb.append('"').append(">");

    return sb.toString();
  }

  private String buildEndOfElement(String elementName) {

    return "</" + elementName + ">";
  }


  private String buildElement(String elementName, String className, boolean isHidden, String content) {

    return buildStartOfElement(elementName, className, isHidden)
        + content
        + buildEndOfElement(elementName);
  }

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
    currentDetails.add(
        buildElement(
            SPAN_ELEMENT,
            RESOLVE_SYMBOL_CLASS,
            hidden,
          value.getStringResult() + " &larr; " + symbol
        )
    );
  }

  @Override
  public void addAssignSymbol(String symbol, DiceExprResult value) {
    currentDetails.add(
        buildElement(
            SPAN_ELEMENT,
            ASSIGN_SYMBOL_CLASS,
            hidden,
            symbol + " &rarr; " + value.getStringResult()
        )
    );
  }

  @Override
  public void addPromptValue(String prompt, DiceExprResult value) {
    currentDetails.add(
        buildElement(
            SPAN_ELEMENT,
            PROMPT_CLASS,
            hidden,
            "input (" + prompt + ") &rarr; " + value.getStringResult()
        )
    );
  }

  @Override
  public void addRoll(DiceRolls rolls) {
    StringBuilder sb = new StringBuilder();

    sb.append(buildElement(
        SPAN_ELEMENT,
        DICE_EXPRESSION_CLASS,
        hidden,
        rolls.getNumberOfRolls() + rolls.getName() + rolls.getNumberOfSides()
    ));

    String listOfRolls = rolls.getDiceRolls().stream()
            .map(
                r -> {
                  StringBuilder sbRolls = new StringBuilder();
                  sbRolls.append(DIE_ROLL_CLASS);
                  if (r.isSuccess()) {
                    sbRolls.append(" " + SUCCESSFUL_DIE_ROLL_CLASS);
                  }
                  if (r.isFailure()) {
                    sbRolls.append(" " + FAILURE_DIE_ROLL_CLASS);
                  }
                  if (r.isFumble()) {
                    sbRolls.append(" " + CRITICAL_FUMBLE_DIE_ROLL_CLASS);
                  }
                  if (r.isCritical()) {
                    sbRolls.append(" " + CRITICAL_SUCCESS_DIE_ROLL_CLASS);
                  }
                  return buildElement(
                      SPAN_ELEMENT,
                      sbRolls.toString(),
                      hidden,
                      Integer.toString(r.getValue())
                  );
                })
            .collect(Collectors.joining(", ")
        );

    sb.append(" &Rarr; ").append(
        buildElement(
            SPAN_ELEMENT,
            ROLL_RESULT_CLASS,
            hidden,
            "[ " + listOfRolls + " ]"
        )
    );
    sb.append(" &Rarr; ").append(
        buildElement(
            SPAN_ELEMENT,
            ROLL_RESULT_CLASS,
            hidden,
            rolls.getResult().getStringResult()
        )
    );

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

    sb.append(buildElement(SPAN_ELEMENT, DICE_EXPRESSION_CLASS, hidden, details.getExpression()));
    sb.append(" &Rarr; ");
    sb.append(buildElement(SPAN_ELEMENT, RESULT_CLASS, hidden, details.getResult()));
    details.getDetails().forEach(sb::append);
    sb.append(buildEndOfElement(SPAN_ELEMENT));

    return Optional.of(sb.toString());
  }

  @Override
  public Optional<String> format() {
    StringBuilder sbInner = new StringBuilder();
    for (var det : details) {
      format(det).ifPresent(sbInner::append);
    }

    return Optional.of(
        buildElement(
            DIV_ELEMENT,
            INLINE_ROLL_CLASS,
            hidden,
            sbInner.toString()
      )
    );

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
    currentDetails.add(buildStartOfElement(DIV_ELEMENT, INLINE_ROLL_PART_CLASS, hidden));
    currentResult = "";
  }

  @Override
  public void end() {
    currentDetails.add(buildEndOfElement(DIV_ELEMENT));
    details.add(new Details(currentResult, currentDetails, currentExpression));
  }
}
