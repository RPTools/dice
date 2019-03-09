package net.rptools.dice.roller.arguments;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;
import java.util.function.ToIntBiFunction;
import java.util.stream.Collectors;
import net.rptools.dice.DiceExpression;
import net.rptools.dice.expressiontree.DiceExpressionNode;
import net.rptools.dice.result.DiceExprResult;
import net.rptools.dice.result.DiceRolls;
import net.rptools.dice.result.DieRoll;
import net.rptools.dice.result.DieRoll.DieRollFlags;

public class KeepRollerArgument extends AbstractDiceRollerArgument {

  private class DiePosition {
    int position;
    DieRoll dieRoll;

    DiePosition(int pos, DieRoll roll) {
      position = pos;
      dieRoll = roll;
    }
  }

  public static KeepRollerArgument keepHighest(DiceExpressionNode val) {
    // Keep Highest is equivalent to drop lowest (num rolls - to keep) so sort in ascending
    // order to start marking lowest as dropped.
    return new KeepRollerArgument(
        val,
        Comparator.comparingInt(
            d -> d.dieRoll.getValue()
        ),
        (dr, kra) -> {
          int numToKeep = kra.getValue().getIntResult().orElseThrow(() -> new IllegalArgumentException("Expected number of rolls to keep"));
          return dr.getNumberOfRolls() - numToKeep;
        }
    );
  }

  public static KeepRollerArgument keepLowest(DiceExpressionNode val) {
    // Keep Lowest is equivalent to drop highest (num rolls - to keep) so sort in descending
    // order to start marking lowest as dropped.
    return new KeepRollerArgument(
        val,
        Comparator.comparing(
            d -> d.dieRoll.getValue(),
            Comparator.reverseOrder()
        ),
        (dr, kra) -> {
          int numToKeep = kra.getValue().getIntResult().orElseThrow(() -> new IllegalArgumentException("Expected number of rolls to keep"));
          return dr.getNumberOfRolls() - numToKeep;
        }
    );
  }

  public static KeepRollerArgument dropHighest(DiceExpressionNode val) {
    return new KeepRollerArgument(
        val,
        Comparator.comparing(
            d -> d.dieRoll.getValue(),
            Comparator.reverseOrder()
        ),
        (dr, kra) -> kra.getValue().getIntResult().orElseThrow(() -> new IllegalArgumentException("Expected number of rolls to "))
    );
  }

  public static KeepRollerArgument dropLowest(DiceExpressionNode val) {
    return new KeepRollerArgument(
        val,
        Comparator.comparingInt(
            d -> d.dieRoll.getValue()
        ),
        (dr, kra) -> kra.getValue().getIntResult().orElseThrow(() -> new IllegalArgumentException("Expected number of rolls to "))
    );
  }


  private final Comparator<DiePosition> comparator;
  private final ToIntBiFunction<DiceRolls, KeepRollerArgument> numberToDrop;


  private KeepRollerArgument(DiceExpressionNode val, Comparator<DiePosition> comp, ToIntBiFunction<DiceRolls, KeepRollerArgument> numToDrop) {
    super("=", val);
    comparator = comp;
    numberToDrop = numToDrop;
  }

  @Override
  public DiceRolls applyToAll(DiceRolls rolls) {

    int numToDrop = numberToDrop.applyAsInt(rolls, this);

    if (numToDrop >= rolls.getNumberOfRolls()) {
      return DiceRolls.NO_ROLLS;
    }

    AtomicInteger counter = new AtomicInteger(0);
    var drop = rolls.getDiceRolls().stream().map(r -> new DiePosition(counter.getAndIncrement(), r)).sorted(comparator).limit(numToDrop).collect(Collectors.toList());

    List<DieRoll> newRolls = new LinkedList<>(rolls.getDiceRolls());
    for (var d : drop) {
      newRolls.set(d.position, newRolls.get(d.position).withAddedFlag(DieRollFlags.DROPPED));
    }

    return rolls.applyNewRolls(newRolls);
  }

  @Override
  public String getArgumentName() {
    return "keep highest";
  }
}
