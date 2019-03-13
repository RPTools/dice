package net.rptools.dice.result.html;

import java.util.Collection;
import net.rptools.dice.result.DiceExprResult;
import net.rptools.dice.result.DiceRolls;
import net.rptools.dice.result.DieRoll;

class Detail {

  public enum DetailType {
    DICE_ROLL,
    VARIABLE_ASSIGNMENT,
    VARIABLE_RESOLVE,
    PROMPT
  }

  private final DetailType detailType;
  private final DiceRolls diceRolls;
  private final String symbolName;
  private final DiceExprResult result;


  private Detail(DetailType dtype, DiceRolls rolls, String symName, DiceExprResult res) {
    detailType = dtype;
    diceRolls = rolls;
    symbolName = symName;
    result = res;
  }

  static Detail createDiceRoll(DiceRolls rolls) {
    return new Detail(DetailType.DICE_ROLL, rolls, null, rolls.getResult());
  }

  static Detail createVariableAssignement(String name, DiceExprResult res) {
    return new Detail(DetailType.VARIABLE_ASSIGNMENT, null, name, res);
  }

  static Detail createVariableResove(String name, DiceExprResult res) {
    return new Detail(DetailType.VARIABLE_RESOLVE, null, name, res);
  }

  static Detail createPrompt(String name, DiceExprResult res) {
    return new Detail(DetailType.PROMPT, null, name, res);
  }




  public DetailType getDetailType() {
    return detailType;
  }

  public DiceRolls getDiceRolls() {
    return diceRolls;
  }

  public String getSymbolName() {
    return symbolName;
  }

  public DiceExprResult getResult() {
    return result;
  }




  public String getValue() {
    return result.getStringResult();
  }

  public boolean isRoll() {
    return detailType == DetailType.DICE_ROLL;
  }

  public boolean isPrompt() {
    return detailType == DetailType.PROMPT;
  }

  public boolean isAssignment() {
    return detailType == DetailType.VARIABLE_ASSIGNMENT;
  }

  public boolean isLookup() {
    return detailType == DetailType.VARIABLE_RESOLVE;
  }

  public String getExpression() {
    switch (detailType) {
      case DICE_ROLL:
        return diceRolls.getNumberOfRolls() + diceRolls.getName() + diceRolls.getNumberOfSides();
      default:
        // TODO:
        return "TODO:";

    }
  }

  public String getDelimiter() {
    return diceRolls.getAggregateMethod().getOutputSeparator();
  }

  public Collection<DieRoll> getRolls() {
    return diceRolls.getDiceRolls();
  }
}
