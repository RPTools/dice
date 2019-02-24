package net.rptools.maptool.dice.expressiontree;

import net.rptools.maptool.dice.result.DiceExprResult;
import net.rptools.maptool.dice.symbols.DiceExpressionSymbolTable;

import java.util.Collection;
import java.util.Collections;

public class TopLevelExpressionNode implements  DiceExpressionNode {

    private final DiceExpressionNode child;
    private final String expression;


    public TopLevelExpressionNode(String expression, DiceExpressionNode child) {
        this.expression = expression;
        this.child = child;
    }

    @Override
    public DiceExprResult evaluate(DiceExpressionSymbolTable symbolTable) throws UnsupportedOperationException {
        return child.evaluate(symbolTable);
    }

    @Override
    public DiceExprResult getExprResult() {
        return child.getExprResult();
    }

    @Override
    public Collection<DiceExpressionNode> getChildren() {
        return Collections.singletonList(child);
    }

    public String getExpression() {
        return expression;
    }
}
