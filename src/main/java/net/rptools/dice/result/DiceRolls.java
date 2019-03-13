/*
 * This software Copyright by the RPTools.net development team, and
 * licensed under the Affero GPL Version 3 or, at your option, any later
 * version.
 *
 * MapTool Source Code is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU Affero General Public
 * License * along with this source Code.  If not, please visit
 * <http://www.gnu.org/licenses/> and specifically the Affero license
 * text at <http://www.gnu.org/licenses/agpl.html>.
 */
package net.rptools.dice.result;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/** This class holds a collection of {@link DieRoll}s and an aggregation of their flagged values. */
public class DiceRolls {

  /**
   * How the result should be calculated.
   */
  public enum DiceRollAggregateMethod {
    /** Count of non dropped rolls. */
    COUNT(", ", dr ->
        DiceExprResult.getIntResult((int)dr.getDiceRolls().stream().filter(r -> r.isDropped() == false).count())
    ),
    /** Sum of non dropped rolls. */
    SUM(" + ", dr ->
        DiceExprResult.getIntResult(
            dr.getDiceRolls().stream().filter(r -> r.isKept()).mapToInt(r -> r.getValue()).sum()
        )
    ),
    /** Count of successful non dropped rolls. */
    COUNT_SUCCESS(", ", dr ->
        DiceExprResult.getIntResult(
            (int) dr.getDiceRolls().stream().filter(r -> r.isKept()).filter(r -> r.isSuccess()).count()
        )
    ),
    /** Count of failed non dropped rolls. */
    COUNT_FAILURE(", ", dr ->
        DiceExprResult.getIntResult(
            (int) dr.getDiceRolls().stream().filter(r -> r.isKept()).filter(r -> r.isFailure()).count()
        )
    ),
    /** Sum the successful non dropped rolls. */
    SUM_SUCCESS(" + ", dr ->
        DiceExprResult.getIntResult(
            dr.getDiceRolls().stream().filter(r -> r.isKept()).filter(r -> r.isSuccess()).mapToInt(r -> r.getValue()).sum()
        )
    ),
    /** Sum the successful non dropped rolls. */
    SUM_FAILURE(" + ", dr ->
        DiceExprResult.getIntResult(
            dr.getDiceRolls().stream().filter(r -> r.isKept()).filter(r -> r.isFailure()).mapToInt(r -> r.getValue()).sum()
        )
    ),
    /** Other. */
    OTHER(", ", dr -> dr.result);


    private final String outputSeparator;
    private final Function<DiceRolls, DiceExprResult> aggregate;


    DiceRollAggregateMethod(String sep, Function<DiceRolls, DiceExprResult> agg) {
      outputSeparator = sep;
      aggregate = agg;
    }

    DiceExprResult perform(DiceRolls roll) {
      return aggregate.apply(roll);
    }

    public String getOutputSeparator() {
      return outputSeparator;
    }


  }

  /** The {@link DieRoll} that make up this list of rolls. */
  private final List<DieRoll> diceRolls;

  /** The number of successes recorded. */
  private final int successes;

  /** The number of failures recorded. */
  private final int failures;

  /** The number of criticals recoded. */
  private final int criticals;

  /** the number of fumbles recorded. */
  private final int fumbles;

  /** The number of rolls performed. */
  private final int numberOfRolls;

  /** The number of sides for the dice. */
  private final int numberOfSides;

  /** The end result of the dice roll. */
  private final DiceExprResult result;

  /** Dice roll name. */
  private final String name;


  /** The method used for determining how the result should be calculated. */
  private final DiceRollAggregateMethod aggregateMethod;

  /** No dice were rolled in the create of this value. */
  @SuppressWarnings("WeakerAccess")
  public static DiceRolls NO_ROLLS =
      new DiceRolls(Collections.emptyList(), 0, DiceExprResult.getIntResult(0), "none");


  /**
   * Creates a new <code>DiceRoll</code> object.
   * @param rolls The rolls that make up the result.
   * @param sides The number of sides for the rolls.
   * @param res The result of the dice rolls (only used for {@link DiceRollAggregateMethod#OTHER}
   * @param rollName The name of the dice roll.
   * @param agg The method used for determining the result of the dice rolls.
   */
  private DiceRolls(Collection<DieRoll> rolls, int sides, DiceExprResult res, String rollName, DiceRollAggregateMethod agg) {
    numberOfSides = sides;
    result = res;
    diceRolls = List.copyOf(rolls);
    name = rollName;
    aggregateMethod = agg;

    int numSuccess = 0;
    int numFail = 0;
    int numCritical = 0;
    int numFumble = 0;

    for (var d : diceRolls) {
      if (d.isFumble()) {
        numFumble++;
      }

      if (d.isFailure()) {
        numFail++;
      }

      if (d.isSuccess()) {
        numSuccess++;
      }

      if (d.isCritical()) {
        numCritical++;
      }
    }

    fumbles = numFumble;
    failures = numFail;
    successes = numSuccess;
    criticals = numCritical;

    numberOfRolls = diceRolls.size();
  }

  /**
   * Creates a new <code>DiceRoll</code> object with a pre-calculated result.
   * @param rolls The rolls that make up the result.
   * @param sides The number of sides for the rolls.
   * @param res The result of the dice rolls (only used for {@link DiceRollAggregateMethod#OTHER}
   * @param rollName The name of the dice roll.
   */
  public DiceRolls(Collection<DieRoll> rolls, int sides, DiceExprResult res, String rollName) {
    this(rolls, sides, res, rollName, DiceRollAggregateMethod.OTHER);
  }


  /**
   * Creates a new <code>DiceRoll</code> object.
   * @param rolls The rolls that make up the result.
   * @param sides The number of sides for the rolls.
   * @param rollName The name of the dice roll.
   * @param agg The method used for determining the result of the dice rolls.
   */
  public DiceRolls(Collection<DieRoll> rolls, int sides, String rollName, DiceRollAggregateMethod agg) {
    this(rolls, sides, DiceExprResult.UNDEFINED, rollName, agg);
  }


    /**
     * Returns the {@link DieRoll}s that make up this collection of rolls.
     *
     * @return the {@link DieRoll}s that make up the collection.
     */
  public Collection<DieRoll> getDiceRolls() {
    return diceRolls;
  }

  /**
   * Returns the number of successes recorded in the rolls.
   *
   * @return the number of successs recorded.
   */
  @SuppressWarnings("WeakerAccess")
  public int getSuccesses() {
    return successes;
  }

  /**
   * Returns the number of failures recorded in the rolls.
   *
   * @return the number of failures recorded.
   */
  @SuppressWarnings("WeakerAccess")
  public int getFailures() {
    return failures;
  }

  /**
   * Returns the number of criticals recorded in the rolls.
   *
   * @return the number of criticals recorded.
   */
  @SuppressWarnings("WeakerAccess")
  public int getCriticals() {
    return criticals;
  }

  /**
   * Returns the number of fumbles recorded in the rolls.
   *
   * @return the number of fumbles recorded.
   */
  @SuppressWarnings("WeakerAccess")
  public int getFumbles() {
    return fumbles;
  }

  /**
   * Returns the number of rolls performed.
   *
   * @return the number of rolls performed.
   */
  public int getNumberOfRolls() {
    return numberOfRolls;
  }

  /**
   * Returns the number of sides for the dice rolled.
   *
   * @return the number of sides for the dice rolled.
   */
  public int getNumberOfSides() {
    return numberOfSides;
  }

  /**
   * Returns the end result of the dice rolls.
   *
   * @return the end result of the dice rolls.
   */
  public DiceExprResult getResult() {
    return aggregateMethod.perform(this);
  }

  /**
   * Returns the name of the dice that were rolled.
   *
   * @return the name of the dice that were rolled.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the method used to determine the result.
   * @return The method used to determine the result.
   */
  public DiceRollAggregateMethod getAggregateMethod() {
    return aggregateMethod;
  }

  /**
   * Clones this <code>DiceRolls</code> with a different {@link DiceRollAggregateMethod}.
   *
   * @param method The new {@link DiceRollAggregateMethod} to use for the roll.
   * @return The new <code>DiceRolls</code>.
   */
  public DiceRolls applyNewAggregateMethod(DiceRollAggregateMethod method) {
    return new DiceRolls(diceRolls, numberOfSides, getResult(), name, method);
  }

  /**
   * Clones this <code>DiceRolls</code> with different rolls.
   *
   * @param rolls The new rolls to use.
   * @return The new <code>DiceRolls</code>.
   */
  public DiceRolls applyNewRolls(Collection<DieRoll> rolls) {
    return new DiceRolls(rolls, numberOfSides, getResult(), name, aggregateMethod);
  }

  /**
   * Clones this <code>DiceRolls</code> with different total result.
   *
   * @param res The new total result.
   * @return The new <code>DiceRolls</code>.
   */
  public DiceRolls applyNewResult(DiceExprResult res) {
    return new DiceRolls(diceRolls, numberOfSides, res, name, aggregateMethod);
  }
}
