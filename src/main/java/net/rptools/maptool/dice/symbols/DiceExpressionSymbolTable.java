package net.rptools.maptool.dice.symbols;

import net.rptools.maptool.dice.DiceExprResultOld;

import java.util.Set;

/**
 * Interface implemented for symbol tables that can be used to resolve symbols for dice expression evaluation.
 */
public interface DiceExpressionSymbolTable {

    /**
     * Returns the value that has been stored in a variable.
     * @param scope The scope of the variable.
     * @param name The name of the variable to get the value for.
     * @return the value stored against the variable.
     */
    DiceExprResultOld getVariableValue(DiceEvalScope scope, String name);

    /**
     * Checks to see if the specified variable has been defined in a certain scope.
     * @param scope The scope to check for teh variable.
     * @param name The name of the variable to check for.
     * @return <code>true</code> if the variable exists for the scope.
     */
    boolean containsVariable(DiceEvalScope scope, String name);

    /**
     * Sets the value for a variable in the spcified scope.
     * @param scope The scope for the variable.
     * @param name The name of the variable.
     * @param value The value to set the variable to.
     */
    void setVariableValue(DiceEvalScope scope, String name, DiceExprResultOld value);

    /**
     * Returns the names of the variables defined for the specified scope.
     * @param scope The scope to get the variables for.
     * @return a {@link Set} containing the names of the variables in the scope.
     */
    Set<String> getVariableNames(DiceEvalScope scope);

}
