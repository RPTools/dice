package net.rptools.maptool.dice.symbols;

import net.rptools.maptool.dice.DiceExprResultOld;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Default symbol table for variable resolution during evaluating dice expressions.
 */
public class DefaultDiceExpressionSymbolTable implements DiceExpressionSymbolTable {

    /** The variables that have been set and their scopes. */
    private final Map<DiceEvalScope, Map<String, DiceExprResultOld>> variables = new HashMap<>();


    /**
     * Creates a new {@link DefaultDiceExpressionSymbolTable}.
     */
    public DefaultDiceExpressionSymbolTable() {
        DiceEvalScope[] scopes = DiceEvalScope.values();
        for (int i = 0; i < scopes.length; i++) {
            variables.put(scopes[i], new HashMap<>());
        }
    }

    @Override
    public DiceExprResultOld getVariableValue(DiceEvalScope scope, String name) {
        return variables.get(scope).get(name);
    }

    @Override
    public boolean containsVariable(DiceEvalScope scope, String name) {
        return variables.get(scope).containsKey(name);
    }

    @Override
    public void setVariableValue(DiceEvalScope scope, String name, DiceExprResultOld value) {
        variables.get(scope).put(name, value);
    }

    @Override
    public Set<String> getVariableNames(DiceEvalScope scope) {
        return variables.get(scope).keySet();
    }
}
