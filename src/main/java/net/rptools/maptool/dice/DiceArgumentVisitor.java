package net.rptools.maptool.dice;

import net.rptools.maptool.dice.roller.DiceRollerArgument;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Visitor used to visit dice expression arguments in the parse expressiontree.
 */
public class DiceArgumentVisitor extends DiceExprBaseVisitor<List<DiceRollerArgument>> {

    @Override
    public List<DiceRollerArgument> visitDiceArgument(DiceExprParser.DiceArgumentContext ctx) {
        String name = ctx.name.getText();
        String op = ctx.op != null ? ctx.op.getText() : null;

        List<DiceRollerArgument> args = new ArrayList<>();

        if (ctx.val == null) {
            args.add(new DiceRollerArgument(name));
        } else {
            int number = Integer.parseInt(ctx.val.getText());
            args.add(new DiceRollerArgument(name, op, number));
        }

        return args;
    }

    @Override
    public List<DiceRollerArgument> visitDiceArgumentList(DiceExprParser.DiceArgumentListContext ctx) {
        return ctx.diceArgument().stream().map(arg -> arg.accept(this)).flatMap(Collection::stream).collect(Collectors.toList());
    }
}
