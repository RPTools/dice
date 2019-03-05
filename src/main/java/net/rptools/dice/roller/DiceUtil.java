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
package net.rptools.dice.roller;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/** Utility class for generating random numbers for dice rolls. */
public class DiceUtil {

  /** Singleton instance. */
  private static final DiceUtil instance = new DiceUtil();

  /** Private constructor to stop instantiation. */
  private DiceUtil() {}

  /**
   * Returns an instance of <code>DiceUtil</code>.
   *
   * @return an instance of <code>DiceUtil</code>.
   */
  @SuppressWarnings("WeakerAccess")
  public static DiceUtil getInstance() {
    return instance;
  }

  /** Random number generator. */
  private static final Random random = new SecureRandom();

  /**
   * Returns a random integer between the lower and upper bounds inclusive.
   *
   * @param lower The lower bound (inclusive) for the random number.
   * @param upper The upper bound (inclusive) for the random number..
   * @return a random int.
   */
  @SuppressWarnings("WeakerAccess")
  public int randomInt(int lower, int upper) {
    return randomIntsArray(1, lower, upper)[0];
  }

  /**
   * Returns an {@link IntStream} of random integers between the lower and upper bounds inclusive.
   *
   * @param number The number of random numbers to generate.
   * @param lower The lower bound (inclusive) for the random numbers;
   * @param upper The upper bound (inclusive) for the random numbers;
   * @return an {@link IntStream} of random integers.
   */
  @SuppressWarnings("WeakerAccess")
  public IntStream randomInts(int number, int lower, int upper) {
    if (lower == upper) {
      int[] arr = new int[number];
      Arrays.fill(arr, lower);
      return Arrays.stream(arr);
    }
    return random.ints(number, lower, upper + 1);
  }

  /**
   * Returns an array of random integers between the lower and upper bounds inclusive.
   *
   * @param number The number of integers to return in the array.
   * @param lower The lower bound (inclusive) for the random numbers.
   * @param upper The upper bound (inclusive) for the random numbers.
   * @return an array of random integers.
   */
  @SuppressWarnings("WeakerAccess")
  public int[] randomIntsArray(int number, int lower, int upper) {
    return randomInts(number, lower, upper).toArray();
  }

  /**
   * Returns a random number representing a die roll.
   *
   * @param sides The number of sides for the die.
   * @return a random number representing a die roll.
   */
  @SuppressWarnings("unused")
  public int randomDieRoll(int sides) {
    return randomInt(1, sides);
  }

  /**
   * Returns an {@link IntStream} of random numbers representing dice rolls.
   *
   * @param number The number of dice.
   * @param sides The number of sides for the dice.
   * @return an {@link IntStream} of random numbers representing dice rolls.
   */
  @SuppressWarnings("unused")
  public IntStream randomDiceRolls(int number, int sides) {
    return randomInts(number, 1, sides);
  }

  /**
   * Returns an array of random numbers representing dice rolls.
   *
   * @param number The number of dice.
   * @param sides The number of sides for the dice.
   * @return an array of random numbers representing dice rolls.
   */
  @SuppressWarnings("WeakerAccess")
  public int[] randomDiceRollsArray(int number, int sides) {
    return randomIntsArray(number, 1, sides);
  }
}
