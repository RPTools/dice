package net.rptools.maptool.dice.result;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class PlainResultFormatter implements ResultFormatter {


    private static class Details {
        private final List<String> details;
        private final String result;
        private final String expression;

        public Details(String res, Collection<String> det, String expr) {
            result = res;
            details = List.copyOf(det);
            expression = expr;
        }


        public String getResult() {
            return result;
        }

        public List<String> getDetails() {
            return details;
        }

        public String getExpression() {
            return expression;
        }
    }

    private List<String> currentDetails = new LinkedList<>();
    private String currentResult = "";
    private String currentExpression = "";


    private final List<Details> details = new LinkedList<>();


    private boolean hidden = false;


    @Override
    public void setResult(DiceExprResult result) {
        currentResult = result.getStringResult();
    }

    @Override
    public void setExpression(String expression) {
        currentExpression = expression;
    }

    @Override
    public void addResolveSymbol(String symbol, DiceExprResult value) {
        currentDetails.add(value.getStringResult() + " <- " + symbol);
    }

    @Override
    public void addAssignSymbol(String symbol, DiceExprResult value) {
        currentDetails.add("set " + symbol + " = " + value.getStringResult());
    }

    @Override
    public void addPromptValue(String prompt, DiceExprResult value) {
        currentDetails.add("input (" + prompt + ") = " + value.getStringResult());
    }

    @Override
    public void addRoll(DiceRolls rolls) {
        StringBuffer sb = new StringBuffer();
        sb.append(rolls.getNumberOfRolls()).append(rolls.getName()).append(rolls.getNumberOfSides())
                .append(" = ").append(rolls.getResult().getStringResult());
        currentDetails.add(sb.toString());
    }


    private Optional<String> format(Details details) {
        StringBuffer sb = new StringBuffer();

        if (details.getResult() == null || details.getResult().length() == 0) {
            return Optional.empty();
        }

        sb.append(details.getExpression()).append(" = ").append(details.getResult()).append("\n");

        details.getDetails().stream().forEach(l -> sb.append("\t").append(l).append("\n"));


        return Optional.of(sb.toString());
    }

    @Override
    public Optional<String> format() {
        StringBuffer sb = new StringBuffer();
        boolean first = true;
        for (var det : details) {
            if (!first) {
                sb.append("------------------------------------------------------\n");
            } else {
                first = false;
            }
            var str = format(det);
            if (str.isPresent()) {
                sb.append(str.get());
            }
        }

        if (sb.length() > 0) {
            return Optional.of(sb.toString());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void hideOutput() {
        hidden = true;
    }

    @Override
    public void showOutput() {
        hidden = false;
    }

    @Override
    public boolean isOutputHidden() {
        return hidden;
    }

    @Override
    public void start() {
        currentDetails = new LinkedList<>();
        currentResult = "";
    }

    @Override
    public void end() {
        details.add(new Details(currentResult, currentDetails, currentExpression));
    }

}
