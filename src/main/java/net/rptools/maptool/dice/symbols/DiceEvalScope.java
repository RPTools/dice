package net.rptools.maptool.dice.symbols;

/**
 * This enum represents the scopes for the dice expression evaluator symbol tables..
 */
public enum DiceEvalScope {

    /** Local scope, soon forgotten. */
    LOCAL("local", "$"),
    /** Global scope, used for updating/reading flags. */
    GLOBAL("global", "%"),
    /** Property scope, used for reading the property of the current token. */
    PROPERTY("property", "@");

    /** The name of the scope. */
    private final String scopeName;

    /** The prefix of the scope. */
    private final String scopePrefix;


    /**
     * Creates a new <code>DiceEvalScope</code> enumerated value.
     * @param name The name of the scope.
     * @param prefix The prefix for the scope.
     */
    DiceEvalScope(String name, String prefix) {
        scopeName = name;
        scopePrefix = prefix;
    };

    /**
     * Returns the name of the scope.
     * @return the name of the scope.
     */
    public String getScopeName() {
        return scopeName;
    }

    /**
     * Returns the prefix of the scope.
     * @return the profix of the scope.
     */
    public String getScopePrefix() {
        return scopePrefix;
    }

    public static DiceEvalScope getScopeForPrefix(String scopePrefix) {
        for (var scope : values()) {
            if (scope.getScopePrefix().equals(scopePrefix)) {
                return scope;
            }
        }

        // TODO: should throw an exception here.
        return null;
    }
}
