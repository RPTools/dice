package net.rptools.maptool.dice.expressiontree;

import net.rptools.maptool.dice.result.ResultFormatter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


public class ResultDetails {

    public Optional<String> format(Collection<DiceExpressionNode> roots, ResultFormatter formatter) {
        roots.stream().forEach(r -> format(r, formatter));

        return formatter.format();
    }

    private void format(DiceExpressionNode root, ResultFormatter formatter) {

        formatter.start();

        List<DiceExpressionNode> nodeList = new ArrayList<>();

        visitNode(root, nodeList);

        formatter.setResult(root.getExprResult());

        for (var node : nodeList) {
            if (node instanceof AssignmentDiceExpressionNode) {
                var aNode = (AssignmentDiceExpressionNode) node;
                formatter.addAssignSymbol(aNode.getVariableName(), aNode.getExprResult());
            } else if (node instanceof DiceRollDiceExpressionNode) {
                formatter.addRoll(node.getExprResult().getDiceRolls());
            } else if (node instanceof InstructionDiceExpressionNode) {
                var iNode = (InstructionDiceExpressionNode) node;
                if ("show".equalsIgnoreCase(iNode.getInstructionName())) {
                    formatter.showOutput();
                } else if ("hide".equalsIgnoreCase(iNode.getInstructionName())) {
                    formatter.hideOutput();
                }
            } else if (node instanceof ResolveSymbolDiceExpressionNode) {
                var rNode = (ResolveSymbolDiceExpressionNode) node;
                formatter.addResolveSymbol(rNode.getVariableName(), rNode.getExprResult());
            } else if (node instanceof TopLevelExpressionNode) {
                var tNode = (TopLevelExpressionNode) node;
                formatter.setExpression(tNode.getExpression());
            }
        }

        formatter.end();

    }


    private void visitNode(DiceExpressionNode root, List<DiceExpressionNode> nodeList) {
        root.getChildren().stream().forEach(r -> visitNode(r, nodeList));
        nodeList.add(root);
    }

}
