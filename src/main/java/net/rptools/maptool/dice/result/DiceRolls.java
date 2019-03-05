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
package net.rptools.maptool.dice.result;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/** This class holds a collection of {@link DieRoll}s and an aggregation of their flagged values. */
public class DiceRolls {

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

  /** No dice were rolled in the create of this value. */
  @SuppressWarnings("WeakerAccess")
  public static DiceRolls NO_ROLLS =
      new DiceRolls(Collections.emptyList(), 0, DiceExprResult.getIntResult(0), "none");

  /**
   * Creates a new <code>DiceRolls</code> to hold a collection of {@link DieRoll}s.
   *
   * @param rolls the {@link DieRoll}s.
   */
  public DiceRolls(Collection<DieRoll> rolls, int sides, DiceExprResult res, String rollName) {
    numberOfSides = sides;
    result = res;
    diceRolls = List.copyOf(rolls);
    name = rollName;

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
    return result;
  }

  /**
   * Returns the name of the dice that were rolled.
   *
   * @return the name of the dice that were rolled.
   */
  public String getName() {
    return name;
  }
}
