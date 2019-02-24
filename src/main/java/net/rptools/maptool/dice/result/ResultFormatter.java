package net.rptools.maptool.dice.result;

import java.util.Optional;

public interface ResultFormatter {

    void setResult(DiceExprResult result);

    void setExpression(String expression);

    void addResolveSymbol(String symbol, DiceExprResult value);

    void addAssignSymbol(String symbol, DiceExprResult value);

    void addPromptValue(String prompt, DiceExprResult value);

    void addRoll(DiceRolls rolls);

    Optional<String> format();

    void hideOutput();

    void showOutput();

    boolean isOutputHidden();

    void start();

    void end();

}
