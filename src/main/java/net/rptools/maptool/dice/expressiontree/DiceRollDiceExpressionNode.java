package net.rptools.maptool.dice.expressiontree;

import net.rptools.maptool.dice.result.DiceExprResult;
import net.rptools.maptool.dice.result.DiceRolls;
import net.rptools.maptool.dice.roller.DiceRollerArgument;
import net.rptools.maptool.dice.roller.DiceRollers;
import net.rptools.maptool.dice.symbols.DiceExpressionSymbolTable;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * None that represents rolling of dice
 */
public class DiceRollDiceExpressionNode implements DiceExpressionNode {


    /** the number of sides on the dice. **/
    private final DiceExpressionNode numberOfSides;

    /** The number of dice to roll. */
    private final DiceExpressionNode numberOfDice;

    /** The name of the dice. */
    private final String diceName;

    /** The input string for the roll. */
    private final String diceString;

    /** The arguments to the dice roller. */
    private final List<DiceRollerArgument> rollerArguments;

    /** The result of evaluating the expression. */
    private DiceExprResult result;



    /**
     * Create a node to hold a dice roll.
     * @param name The name of the dice.
     * @param numDice The number of dice to roll.
     * @param numSides The number of sides om the dice.
     * @param args The arguments to the dice roll.
     * @param str The dice roll string..
     */
    public DiceRollDiceExpressionNode(String name, DiceExpressionNode numDice, DiceExpressionNode numSides, Collection<DiceRollerArgument> args, String str) {
        diceName = name;
        rollerArguments = List.copyOf(args);
        diceString = str;
        numberOfSides = numSides;
        numberOfDice = numDice;
    }

    @Override
    public DiceExprResult evaluate(DiceExpressionSymbolTable symbolTable) throws UnsupportedOperationException {
        int dice = numberOfDice.evaluate(symbolTable).getIntResult().getAsInt();
        int sides = numberOfSides.evaluate(symbolTable).getIntResult().getAsInt();
        result = DiceRollers.getInstance().getDiceRoller(diceName).roll(diceName, dice, sides);
        return result;
    }

    @Override
    public DiceExprResult getExprResult() {
        return result;
    }

    @Override
    public Collection<DiceExpressionNode> getChildren() {
        return Arrays.asList(new DiceExpressionNode[] { numberOfDice, numberOfSides });
    }

    /**
     * Format the rolls as a String for display.
     * @param diceRolls the rolls to format.
     * @return a formatted string for the rolls.
     */
    private String formatRolls(DiceRolls diceRolls) {

        StringBuffer sb = new StringBuffer();
        LinkedList<String> rolls = new LinkedList<>();

        for (var dr : diceRolls.getDiceRolls()) {
            sb.setLength(0);
            sb.append("<span class=\"dieRoll");
            if (dr.isCritical()) {
                sb.append(" criticalRoll");
            }

            if (dr.isFumble()) {
                sb.append(" fumbleRoll");
            }

            if (dr.isSuccess()) {
                sb.append(" successRoll");
            }

            if (dr.isFailure()) {
                sb.append(" failureRoll");
            }

            sb.append('"').append(">").append(dr.getValue()).append("</span>");


            rolls.add(sb.toString());

        }

        return rolls.stream().collect(Collectors.joining(","));

    }
}
