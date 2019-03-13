package net.rptools.dice.result.html;

import java.util.LinkedList;
import java.util.List;
import net.rptools.dice.result.DiceExprResult;

class Result {

  private DiceExprResult result;
  private String expression;
  private boolean hidden;
  private final List<Detail> details = new LinkedList<>();

  Result() {}

  public DiceExprResult getResult() {
    return result;
  }

  public void setResult(DiceExprResult res) {
    result = res;
  }

  public String getExpression() {
    return expression;
  }

  public void setExpression(String exp) {
    expression = exp;
  }

  public List<Detail> getDetails() {
    return details;
  }

  public void addDetail(Detail detail) {
    details.add(detail);
  }

  public boolean isHidden() {
    return hidden;
  }

  public void setHidden(boolean hide) {
    hidden = hide;
  }

  public String getValue() {
    return result.getStringResult();
  }
}
