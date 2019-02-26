package net.rptools.maptool.dice.symbols;


import net.rptools.maptool.dice.result.DiceExprResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Default symbol table for variable resolution during evaluating dice expressions.
 */
public class DefaultDiceExpressionSymbolTable implements DiceExpressionSymbolTable {

    /** The variables that have been set and their scopes. */
    private final Map<DiceEvalScope, Map<String, DiceExprResult>> variables = new HashMap<>();


    /**
     * Creates a new {@link DefaultDiceExpressionSymbolTable}.
     */
    public DefaultDiceExpressionSymbolTable() {
        DiceEvalScope[] scopes = DiceEvalScope.values();
        for (DiceEvalScope scope : scopes) {
            variables.put(scope, new HashMap<>());
        }
    }

    @Override
    public DiceExprResult getVariableValue(DiceEvalScope scope, String name) {
        return variables.get(scope).get(name);
    }

    @Override
    public boolean containsVariable(DiceEvalScope scope, String name) {
        return variables.get(scope).containsKey(name);
    }

    @Override
    public void setVariableValue(DiceEvalScope scope, String name, DiceExprResult value) {
        variables.get(scope).put(name, value);
    }

    @Override
    public Set<String> getVariableNames(DiceEvalScope scope) {
        return variables.get(scope).keySet();
    }
}
