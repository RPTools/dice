package net.rptools.dice.roller.arguments;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import net.rptools.dice.expressiontree.DiceExpressionNode;
import net.rptools.dice.result.DiceExprResult;
import net.rptools.dice.result.DiceRolls;
import net.rptools.dice.result.DieRoll;

public class KeepHighestRollerArgument extends AbstractDiceRollerArgument {

  private class DiePosition implements Comparator<DiePosition> {
    int position;
    DieRoll dieRoll;

    DiePosition(int pos, DieRoll roll) {
      position = pos;
      dieRoll = roll;
    }

    @Override
    public int compare(DiePosition o1, DiePosition o2) {
      // we want descending order
      return Integer.compare(o2.dieRoll.getValue(), o1.dieRoll.getValue());
    }
  }


  public KeepHighestRollerArgument(DiceExpressionNode node) {
    super("=", node);
  }

  @Override
  public DiceRolls applyToAll(DiceRolls rolls) {

    DieRoll[] newDieRolls = new DieRoll[rolls.getDiceRolls().size()];
    rolls.getDiceRolls().toArray(newDieRolls);
    int sides = rolls.getNumberOfSides();
    DiceExprResult res = rolls.getResult();
    String rollName = rolls.getName();

    int numToKeep = getValue().getIntResult().orElseThrow(() -> new IllegalArgumentException("Expected number of rolls to keep"));

    AtomicInteger counter = new AtomicInteger(0);
    rolls.getDiceRolls().stream().map(r -> new DiePosition(counter.getAndIncrement(), r)).sorted().collect(Collectors.toList());

    return super.applyToAll(rolls);
  }

  @Override
  public String getArgumentName() {
    return "keep highest";
  }
}
