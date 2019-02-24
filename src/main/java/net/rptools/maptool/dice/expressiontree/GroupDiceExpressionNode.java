package net.rptools.maptool.dice.result.tree;


import net.rptools.maptool.dice.result.DiceExprResult;
import net.rptools.maptool.dice.result.tree.DiceExpressionNode;
import net.rptools.maptool.dice.symbols.DiceExpressionSymbolTable;

import java.util.Collection;
import java.util.Collections;

/**
 * None that represents the grouping operator.
 */
public class GroupDiceExpressionNode implements DiceExpressionNode {

    /**
     * Enumeration of the type of grouping.
     */
    public enum GroupingType {
        PAREN("(", ")"),
        BRACE("{", "}");

        /** The characters used to start the grouping. */
        private final String open;

        /** The characters used to end the grouping. */
        private final String close;

        /**
         * Create the enumerated type.
         * @param open The characters used to start the grouping.
         * @param close The characters used to end the grouping.
         */
         GroupingType(String open, String close) {
            this.open = open;
            this.close = close;
        }

        /**
         * Returns the characters used to start the grouping.
         * @return the characters used to start the grouping.
         */
        public String getOpen() {
             return open;
        }

        /**
         * Returns the characters used to end ghe grouping.
         * @return the characters used to end the grouping.
         */
        public String getClose() {
             return close;
        }

    }

    /** The type of grouping that this is. */
    private final GroupingType groupingType;

    /**
     * The result of this node.
     */
    private DiceExprResult result;


    /**
     * The child node for this group.
     */
    private DiceExpressionNode grouped;

    /**
     * Creates a new <code>GroupDiceExpressionNode</code> object.
     * @param type The type of grouping that it is.
     * @param grouped The node representing what this group contains.
     */
    public GroupDiceExpressionNode(GroupingType type, DiceExpressionNode grouped) {
        groupingType = type;
        this.grouped = grouped;
    }

    @Override
    public DiceExprResult evaluate(DiceExpressionSymbolTable symbolTable) throws UnsupportedOperationException {
        result = grouped.evaluate(symbolTable);
        return result;
    }

    @Override
    public DiceExprResult getExprResult() {
        return result;
    }

    @Override
    public String getFormattedText() {
        if (groupingType == GroupingType.PAREN) {
            return "(" + grouped.getFormattedText() + ")";
        } else {
            return "{" + grouped.getFormattedText() + "}";
        }
    }

    @Override
    public Collection<DiceExpressionNode> getChildren() {
        return Collections.singletonList(grouped);
    }

}
