package net.rptools.maptool.dice.result;

import net.rptools.maptool.dice.symbols.DiceEvalScope;
import net.rptools.maptool.dice.symbols.DiceExpressionSymbolTable;

import java.util.Collection;
import java.util.Collections;

/**
 * Represents the node used to resolve a symbol from the symbol table.
 */
public class ResolveSymbolDiceExpressionNode implements DiceExpressionNode {


    /** The name of the symbol. */
    private final String name;

    /** The scope of the symbol. */
    private final DiceEvalScope scope;

    /** The evaluated value of this node. */
    private DiceExprResult result;

    /**
     * Creates a new node used to resolve a symbol from the symbol table.
     * @param symbol The name of the symbol.
     * @param symbolScope The scope of the symbol.
     */
    public ResolveSymbolDiceExpressionNode(String symbol, DiceEvalScope symbolScope) {
        name = symbol;
        scope = symbolScope;
    }

    @Override
    public DiceExprResult evaluate(DiceExpressionSymbolTable symbolTable) throws UnsupportedOperationException {
        result = symbolTable.getVariableValue(scope, name);
        return result;
    }

    @Override
    public DiceExprResult getExprResult() {
        return result;
    }

    @Override
    public String getFormattedText() {
        StringBuffer sb = new StringBuffer();
        sb.append("<span data-").append(scope.getScopeName()).append("=").append('"').append(scope.getScopePrefix()).
                append('"').append(">").append(getExprResult().getStringResult()).append("</span>");

        return sb.toString();
    }

    @Override
    public Collection<DiceExpressionNode> getChildren() {
        return Collections.emptyList();
    }

}
