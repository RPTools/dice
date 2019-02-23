package net.rptools.maptool.dice.result;

import net.rptools.maptool.dice.symbols.DiceEvalScope;
import net.rptools.maptool.dice.symbols.DiceExpressionSymbolTable;

import java.util.Collection;
import java.util.Collections;

/**
 * Represents the node used to assign a value to a symbol.
 */
public class AssignmentDiceResultOpNode implements DiceResultNode {


    /** The name of the symbol. */
    private final String name;

    /** The scope of the symbol. */
    private final DiceEvalScope scope;


    /** The right hand side of the assignment expression. */
    private final DiceResultNode rhs;


    /** The evaluated value of this node. */
    private DiceExprResult result;

    /**
     * Creates a new node used to assign a value to a symbol.
     * @param symbol The name of the symbol.
     * @param symbolScope The scope of the symbol.
     * @param rhs The right hand side of the assignment.
     */
    public AssignmentDiceResultOpNode(String symbol, DiceEvalScope symbolScope, DiceResultNode rhs) {
        name = symbol;
        scope = symbolScope;
        this.rhs = rhs;
    }

    @Override
    public DiceExprResult evaluate(DiceExpressionSymbolTable symbolTable) throws UnsupportedOperationException {
        result = rhs.evaluate(symbolTable);
        symbolTable.setVariableValue(scope, name, result);
        return result;
    }

    @Override
    public DiceExprResult getExprResult() {
        return result;
    }

    @Override
    public String getFormattedText() {
        System.out.println("############ " + name);
        return name + " = " + rhs.getFormattedText();
    }

    @Override
    public Collection<DiceResultNode> getChildren() {
        return Collections.singletonList(rhs);
    }

}
