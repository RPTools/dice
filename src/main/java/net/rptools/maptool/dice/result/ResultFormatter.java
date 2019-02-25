package net.rptools.maptool.dice.result;

import java.util.Optional;

/**
 * Interface used to denote classes that are used to format the output of a dice roll expression.
 *
 * Classes implementing this interface need to be able to handle the formatting of more than one expression sequentially,
 * formatting the multiple expression in which ever way is suitable for the output.
 *
 * {@link #start()} will be called to signal the details from this point onwards are for a new expression.
 * {@link #end()} will be called to signal all the details for an expression have been sent.
 */
public interface ResultFormatter {

    /**
     * Sets the over all result of the expression.
     *
     * @param result The over all result of the expression.
     */
    void setResult(DiceExprResult result);

    /**
     * Sets the dice roll expression.
     *
     * @param expression the expression
     */
    void setExpression(String expression);

    /**
     * Add a resolved symbol to the details of the output.
     *
     * @param symbol the symbol
     * @param value  the value
     */
    void addResolveSymbol(String symbol, DiceExprResult value);

    /**
     * Add assign to symbol to the details of the output.
     *
     * @param symbol the symbol
     * @param value  the value
     */
    void addAssignSymbol(String symbol, DiceExprResult value);

    /**
     * Add prompt for value to the details of the output.
     *
     * @param prompt the prompt
     * @param value  the value
     */
    void addPromptValue(String prompt, DiceExprResult value);

    /**
     * Add roll expression and details to the details of the output..
     *
     * @param rolls the rolls
     */
    void addRoll(DiceRolls rolls);

    /**
     * Returns the format of the output.
     *
     * @return the formatted output.
     */
    Optional<String> format();

    /**
     * Notify the formatter that subsequent output should be hidden.
     */
    void hideOutput();

    /**
     * Notify the formatter that the subsequent output should not be hidden.
     * Show output.
     */
    void showOutput();

    /**
     * Should subsequent output be hidden.
     *
     * @return <code>true</code> if subsequent output should be hidden.
     */
    boolean isOutputHidden();

    /**
     * Marks the start of a new expression.
     */
    void start();

    /**
     * Marks the end of an expression.
     */
    void end();

}
