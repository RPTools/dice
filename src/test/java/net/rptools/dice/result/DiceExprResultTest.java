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

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import org.junit.jupiter.api.Test;

class DiceExprResultTest {

  /**
   * Helper function that compares two doubles with a small amount of tolerance.
   *
   * @param d1 The first double to compare.
   * @param d2 The second double to compare.
   * @return <code>true</code> if they match within the tolerance.
   */
  private static boolean compareWithTolerance(double d1, double d2) {
    final double TOLERANCE = 1E-8;
    if (Math.abs(d1 - d2) > TOLERANCE) {
      System.out.println(d1 + " - " + d2 + " = " + (d1 - d2));
    }
    return Math.abs(d1 - d2) < TOLERANCE;
  }

  @Test
  void getType() {
    assertSame(DiceExprResult.getIntResult(1).getType(), DiceResultType.INTEGER);
    assertSame(DiceExprResult.getDoubleResult(1.0).getType(), DiceResultType.DOUBLE);
    assertSame(DiceExprResult.getDoubleResult(2).getType(), DiceResultType.DOUBLE);
    assertSame(DiceExprResult.getStringResult("blah").getType(), DiceResultType.STRING);
    assertSame(DiceExprResult.getStringResult("1").getType(), DiceResultType.STRING);
    assertSame(DiceExprResult.getStringResult("2.3").getType(), DiceResultType.STRING);
  }

  @Test
  void getIntResult() {
    assertEquals(3, DiceExprResult.getIntResult(3).getIntResult().orElse(-999));
    assertEquals(4, DiceExprResult.getDoubleResult(4.5).getIntResult().orElse(-999));
    assertEquals(5, DiceExprResult.getStringResult("5").getIntResult().orElse(-999));
    assertEquals(7, DiceExprResult.getStringResult("7.7").getIntResult().orElse(-999));
    assertTrue(DiceExprResult.getStringResult("hello").getIntResult().isEmpty());
  }

  @Test
  void getDoubleResult() {
    assertTrue(
        compareWithTolerance(3.0, DiceExprResult.getIntResult(3).getDoubleResult().orElse(-999.9)));
    assertTrue(
        compareWithTolerance(
            4.5, DiceExprResult.getDoubleResult(4.5).getDoubleResult().orElse(-999.9)));
    assertTrue(
        compareWithTolerance(
            5.0, DiceExprResult.getStringResult("5").getDoubleResult().orElse(-999.9)));
    assertTrue(
        compareWithTolerance(
            7.7, DiceExprResult.getStringResult("7.7").getDoubleResult().orElse(-999.9)));
    assertTrue(DiceExprResult.getStringResult("hello").getDoubleResult().isEmpty());
  }

  @Test
  void getStringResult() {
    assertEquals("3", DiceExprResult.getIntResult(3).getStringResult());
    assertEquals("4.5", DiceExprResult.getDoubleResult(4.5).getStringResult());
    assertEquals("5", DiceExprResult.getStringResult("5").getStringResult());
    assertEquals("7.7", DiceExprResult.getStringResult("7.7").getStringResult());
    assertEquals("hello", DiceExprResult.getStringResult("hello").getStringResult());
  }

  @Test
  void getDiceRolls() {
    assertSame(new DiceExprResult(1).getDiceRolls(), DiceRolls.NO_ROLLS);
    assertSame(new DiceExprResult(1, DiceRolls.NO_ROLLS).getDiceRolls(), DiceRolls.NO_ROLLS);

    var rolls = Collections.singleton(new DieRoll(1, 1));
    var diceRolls = new DiceRolls(rolls, 10, DiceExprResult.getIntResult(1), "Test Dice Roll");
    var diceExprResult =
        new DiceExprResult(diceRolls.getResult().getIntResult().orElse(-999), diceRolls);

    assertEquals(1, diceExprResult.getDiceRolls().getDiceRolls().size());
    assertEquals(diceExprResult.getDiceRolls().getNumberOfRolls(), diceRolls.getNumberOfRolls());
    assertEquals(diceExprResult.getDiceRolls().getNumberOfSides(), diceRolls.getNumberOfSides());
    assertSame(diceExprResult.getDiceRolls().getName(), diceRolls.getName());
  }

  @Test
  void hasRolls() {
    assertFalse(new DiceExprResult(1).hasRolls());
    assertFalse(new DiceExprResult(1, DiceRolls.NO_ROLLS).hasRolls());

    var rolls = Collections.singleton(new DieRoll(1, 1));
    var diceRolls = new DiceRolls(rolls, 10, DiceExprResult.getIntResult(1), "Test Dice Roll");
    var diceExprResult =
        new DiceExprResult(diceRolls.getResult().getIntResult().orElse(-999), diceRolls);

    assertTrue(diceExprResult.hasRolls());
  }

  @Test
  void isNumber() {
    assertTrue(DiceExprResult.getIntResult(1).isNumber());
    assertTrue(DiceExprResult.getDoubleResult(3.4).isNumber());
    assertFalse(DiceExprResult.getStringResult("hello").isNumber());
    assertTrue(DiceExprResult.getStringResult("5").isNumber());
    assertTrue(DiceExprResult.getStringResult("5.6").isNumber());
  }

  @Test
  void getIntResult1() {
    assertTrue(DiceExprResult.getIntResult(1).isNumber());
    assertEquals(6, DiceExprResult.getIntResult(6).getIntResult().orElse(-999));
  }

  @Test
  void getDoubleResult1() {
    assertTrue(DiceExprResult.getDoubleResult(3.8).isNumber());
    assertEquals(99.4, DiceExprResult.getDoubleResult(99.4).getDoubleResult().orElse(-999.9));
  }

  @Test
  void getStringResult1() {
    assertFalse(DiceExprResult.getStringResult("yo!").isNumber());
    assertEquals("red", DiceExprResult.getStringResult("red").getStringResult());
  }

  @Test
  void addInts() {
    var res1 = DiceExprResult.add(DiceExprResult.getIntResult(1), DiceExprResult.getIntResult(3));
    var res2 = DiceExprResult.add(DiceExprResult.getIntResult(5), DiceExprResult.getIntResult(-3));
    var res3 = DiceExprResult.add(DiceExprResult.getIntResult(0), DiceExprResult.getIntResult(3));
    var res4 = DiceExprResult.add(DiceExprResult.getIntResult(0), DiceExprResult.getIntResult(-4));

    assertSame(res1.getType(), DiceResultType.INTEGER);
    assertSame(res1.getIntResult().orElse(-999), 4);
    assertSame(res2.getType(), DiceResultType.INTEGER);
    assertSame(res2.getIntResult().orElse(-999), 2);
    assertSame(res3.getType(), DiceResultType.INTEGER);
    assertSame(res3.getIntResult().orElse(-999), 3);
    assertSame(res4.getType(), DiceResultType.INTEGER);
    assertSame(res4.getIntResult().orElse(-999), -4);
  }

  @Test
  void addIntAndDouble() {
    var res1 =
        DiceExprResult.add(DiceExprResult.getDoubleResult(1.1), DiceExprResult.getIntResult(3));
    var res2 =
        DiceExprResult.add(DiceExprResult.getIntResult(5), DiceExprResult.getDoubleResult(-3.2));
    var res3 =
        DiceExprResult.add(DiceExprResult.getIntResult(0), DiceExprResult.getDoubleResult(3.3));
    var res4 =
        DiceExprResult.add(DiceExprResult.getIntResult(0), DiceExprResult.getDoubleResult(-5.4));

    assertSame(res1.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res1.getDoubleResult().orElse(-999), 4.1));
    assertSame(res2.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res2.getDoubleResult().orElse(-999), 1.8));
    assertSame(res3.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res3.getDoubleResult().orElse(-999), 3.3));
    assertSame(res4.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res4.getDoubleResult().orElse(-999), -5.4));
  }

  @Test
  void addDoubles() {
    var res1 =
        DiceExprResult.add(
            DiceExprResult.getDoubleResult(1.1), DiceExprResult.getDoubleResult(3.2));
    var res2 =
        DiceExprResult.add(
            DiceExprResult.getDoubleResult(5.3), DiceExprResult.getDoubleResult(-3.2));
    var res3 =
        DiceExprResult.add(DiceExprResult.getDoubleResult(0), DiceExprResult.getDoubleResult(3.3));
    var res4 =
        DiceExprResult.add(DiceExprResult.getDoubleResult(0), DiceExprResult.getDoubleResult(-5.4));

    assertSame(res1.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res1.getDoubleResult().orElse(-999), 4.3));
    assertSame(res2.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res2.getDoubleResult().orElse(-999), 2.1));
    assertSame(res3.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res3.getDoubleResult().orElse(-999), 3.3));
    assertSame(res4.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res4.getDoubleResult().orElse(-999), -5.4));
  }

  @Test
  void addStrings() {
    var res1 =
        DiceExprResult.add(
            DiceExprResult.getStringResult("hello"), DiceExprResult.getStringResult("world"));
    var res2 =
        DiceExprResult.add(
            DiceExprResult.getStringResult(""), DiceExprResult.getStringResult("test"));

    assertSame(res1.getType(), DiceResultType.STRING);
    assertEquals(res1.getStringResult(), "helloworld");
    assertSame(res2.getType(), DiceResultType.STRING);
    assertEquals(res2.getStringResult(), "test");
  }

  @Test
  void addStringAndInt() {
    var res1 =
        DiceExprResult.add(DiceExprResult.getStringResult("hello"), DiceExprResult.getIntResult(3));
    var res2 =
        DiceExprResult.add(DiceExprResult.getIntResult(-4), DiceExprResult.getStringResult("test"));

    assertSame(res1.getType(), DiceResultType.STRING);
    assertEquals(res1.getStringResult(), "hello3");
    assertSame(res2.getType(), DiceResultType.STRING);
    assertEquals(res2.getStringResult(), "-4test");
  }

  @Test
  void addStringAndDouble() {
    var res1 =
        DiceExprResult.add(
            DiceExprResult.getStringResult("hello"), DiceExprResult.getDoubleResult(3.2));
    var res2 =
        DiceExprResult.add(
            DiceExprResult.getDoubleResult(-4.2), DiceExprResult.getStringResult("test"));

    assertSame(res1.getType(), DiceResultType.STRING);
    assertEquals(res1.getStringResult(), "hello3.2");
    assertSame(res2.getType(), DiceResultType.STRING);
    assertEquals(res2.getStringResult(), "-4.2test");
  }

  @Test
  void subtractInts() {
    var res1 =
        DiceExprResult.subtract(DiceExprResult.getIntResult(1), DiceExprResult.getIntResult(3));
    var res2 =
        DiceExprResult.subtract(DiceExprResult.getIntResult(5), DiceExprResult.getIntResult(-3));
    var res3 =
        DiceExprResult.subtract(DiceExprResult.getIntResult(0), DiceExprResult.getIntResult(3));
    var res4 =
        DiceExprResult.subtract(DiceExprResult.getIntResult(0), DiceExprResult.getIntResult(-4));

    assertSame(res1.getType(), DiceResultType.INTEGER);
    assertSame(res1.getIntResult().orElse(-999), -2);
    assertSame(res2.getType(), DiceResultType.INTEGER);
    assertSame(res2.getIntResult().orElse(-999), 8);
    assertSame(res3.getType(), DiceResultType.INTEGER);
    assertSame(res3.getIntResult().orElse(-999), -3);
    assertSame(res4.getType(), DiceResultType.INTEGER);
    assertSame(res4.getIntResult().orElse(-999), 4);
  }

  @Test
  void subtractIntAndDouble() {
    var res1 =
        DiceExprResult.subtract(
            DiceExprResult.getDoubleResult(1.1), DiceExprResult.getIntResult(3));
    var res2 =
        DiceExprResult.subtract(
            DiceExprResult.getIntResult(5), DiceExprResult.getDoubleResult(-3.2));
    var res3 =
        DiceExprResult.subtract(
            DiceExprResult.getIntResult(0), DiceExprResult.getDoubleResult(3.3));
    var res4 =
        DiceExprResult.subtract(
            DiceExprResult.getIntResult(0), DiceExprResult.getDoubleResult(-5.4));

    assertSame(res1.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res1.getDoubleResult().orElse(-999), -1.9));
    assertSame(res2.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res2.getDoubleResult().orElse(-999), 8.2));
    assertSame(res3.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res3.getDoubleResult().orElse(-999), -3.3));
    assertSame(res4.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res4.getDoubleResult().orElse(-999), 5.4));
  }

  @Test
  void subtractDoubles() {
    var res1 =
        DiceExprResult.subtract(
            DiceExprResult.getDoubleResult(1.1), DiceExprResult.getDoubleResult(3.2));
    var res2 =
        DiceExprResult.subtract(
            DiceExprResult.getDoubleResult(5.3), DiceExprResult.getDoubleResult(-3.2));
    var res3 =
        DiceExprResult.subtract(
            DiceExprResult.getDoubleResult(0), DiceExprResult.getDoubleResult(3.3));
    var res4 =
        DiceExprResult.subtract(
            DiceExprResult.getDoubleResult(0), DiceExprResult.getDoubleResult(-5.4));

    assertSame(res1.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res1.getDoubleResult().orElse(-999), -2.1));
    assertSame(res2.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res2.getDoubleResult().orElse(-999), 8.5));
    assertSame(res3.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res3.getDoubleResult().orElse(-999), -3.3));
    assertSame(res4.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res4.getDoubleResult().orElse(-999), 5.4));
  }

  @Test
  void subtractStrings() {
    var res1 =
        DiceExprResult.subtract(
            DiceExprResult.getStringResult("helloworld2"), DiceExprResult.getStringResult("world"));
    var res2 =
        DiceExprResult.subtract(
            DiceExprResult.getStringResult(""), DiceExprResult.getStringResult("test"));
    var res3 =
        DiceExprResult.subtract(
            DiceExprResult.getStringResult("test"), DiceExprResult.getStringResult(""));

    assertSame(res1.getType(), DiceResultType.STRING);
    assertEquals(res1.getStringResult(), "hello2");
    assertSame(res2.getType(), DiceResultType.STRING);
    assertEquals(res2.getStringResult(), "");
    assertSame(res3.getType(), DiceResultType.STRING);
    assertEquals(res3.getStringResult(), "test");
  }

  @Test
  void subtractStringAndInt() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            DiceExprResult.subtract(
                DiceExprResult.getStringResult("hello"), DiceExprResult.getIntResult(3)));
    assertThrows(
        IllegalArgumentException.class,
        () ->
            DiceExprResult.subtract(
                DiceExprResult.getIntResult(-4), DiceExprResult.getStringResult("test")));
  }

  @Test
  void subtractStringAndDouble() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            DiceExprResult.subtract(
                DiceExprResult.getStringResult("hello"), DiceExprResult.getDoubleResult(3.2)));
    assertThrows(
        IllegalArgumentException.class,
        () ->
            DiceExprResult.subtract(
                DiceExprResult.getDoubleResult(-4.2), DiceExprResult.getStringResult("test")));
  }

  @Test
  void divideInts() {
    var res1 =
        DiceExprResult.divide(DiceExprResult.getIntResult(1), DiceExprResult.getIntResult(3));
    var res2 =
        DiceExprResult.divide(DiceExprResult.getIntResult(5), DiceExprResult.getIntResult(-3));
    var res3 =
        DiceExprResult.divide(DiceExprResult.getIntResult(0), DiceExprResult.getIntResult(3));
    var res4 =
        DiceExprResult.divide(DiceExprResult.getIntResult(0), DiceExprResult.getIntResult(-4));
    var res5 =
        DiceExprResult.divide(DiceExprResult.getIntResult(-1), DiceExprResult.getIntResult(-4));

    assertSame(res1.getType(), DiceResultType.INTEGER);
    assertSame(res1.getIntResult().orElse(-999), 0);
    assertSame(res2.getType(), DiceResultType.INTEGER);
    assertSame(res2.getIntResult().orElse(-999), -1);
    assertSame(res3.getType(), DiceResultType.INTEGER);
    assertSame(res3.getIntResult().orElse(-999), 0);
    assertSame(res4.getType(), DiceResultType.INTEGER);
    assertSame(res4.getIntResult().orElse(-999), 0);
    assertSame(res5.getType(), DiceResultType.INTEGER);
    assertSame(res5.getIntResult().orElse(-999), 0);
  }

  @Test
  void divideIntAndDouble() {
    var res1 =
        DiceExprResult.divide(DiceExprResult.getDoubleResult(1.1), DiceExprResult.getIntResult(3));
    var res2 =
        DiceExprResult.divide(DiceExprResult.getIntResult(5), DiceExprResult.getDoubleResult(-3.2));
    var res3 =
        DiceExprResult.divide(DiceExprResult.getIntResult(0), DiceExprResult.getDoubleResult(3.3));
    var res4 =
        DiceExprResult.divide(DiceExprResult.getIntResult(0), DiceExprResult.getDoubleResult(-5.4));
    var res5 =
        DiceExprResult.divide(
            DiceExprResult.getIntResult(-1), DiceExprResult.getDoubleResult(-5.4));

    assertSame(res1.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res1.getDoubleResult().orElse(-999), 0.366666666));
    assertSame(res2.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res2.getDoubleResult().orElse(-999), -1.5625));
    assertSame(res3.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res3.getDoubleResult().orElse(-999), 0));
    assertSame(res4.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res4.getDoubleResult().orElse(-999), 0));
    assertSame(res5.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res5.getDoubleResult().orElse(-999), 0.185185185));
  }

  @Test
  void divideDoubles() {
    var res1 =
        DiceExprResult.divide(
            DiceExprResult.getDoubleResult(1.1), DiceExprResult.getDoubleResult(3.2));
    var res2 =
        DiceExprResult.divide(
            DiceExprResult.getDoubleResult(5.3), DiceExprResult.getDoubleResult(-3.2));
    var res3 =
        DiceExprResult.divide(
            DiceExprResult.getDoubleResult(0), DiceExprResult.getDoubleResult(3.3));
    var res4 =
        DiceExprResult.divide(
            DiceExprResult.getDoubleResult(0), DiceExprResult.getDoubleResult(-5.4));
    var res5 =
        DiceExprResult.divide(
            DiceExprResult.getDoubleResult(-1.1), DiceExprResult.getDoubleResult(-5.4));

    assertSame(res1.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res1.getDoubleResult().orElse(-999), 0.34375));
    assertSame(res2.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res2.getDoubleResult().orElse(-999), -1.65625));
    assertSame(res3.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res3.getDoubleResult().orElse(-999), 0));
    assertSame(res4.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res4.getDoubleResult().orElse(-999), 0));
    assertSame(res5.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res5.getDoubleResult().orElse(-999), 0.2037037037037));
  }

  @Test
  void divideStrings() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            DiceExprResult.divide(
                DiceExprResult.getStringResult("hello"), DiceExprResult.getStringResult("world")));
    assertThrows(
        IllegalArgumentException.class,
        () ->
            DiceExprResult.divide(
                DiceExprResult.getStringResult(""), DiceExprResult.getStringResult("test")));
  }

  @Test
  void divideStringAndInt() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            DiceExprResult.divide(
                DiceExprResult.getStringResult("hello"), DiceExprResult.getIntResult(3)));
    assertThrows(
        IllegalArgumentException.class,
        () ->
            DiceExprResult.divide(
                DiceExprResult.getIntResult(-4), DiceExprResult.getStringResult("test")));
    assertThrows(
        IllegalArgumentException.class,
        () ->
            DiceExprResult.divide(
                DiceExprResult.getIntResult(4), DiceExprResult.getStringResult("test")));
    assertThrows(
        IllegalArgumentException.class,
        () ->
            DiceExprResult.divide(
                DiceExprResult.getIntResult(0), DiceExprResult.getStringResult("test")));
  }

  @Test
  void divideStringAndDouble() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            DiceExprResult.divide(
                DiceExprResult.getStringResult("hello"), DiceExprResult.getDoubleResult(3.2)));
    assertThrows(
        IllegalArgumentException.class,
        () ->
            DiceExprResult.divide(
                DiceExprResult.getDoubleResult(-4.2), DiceExprResult.getStringResult("test")));
  }

  @Test
  void multiplyInts() {
    var res1 =
        DiceExprResult.multiply(DiceExprResult.getIntResult(1), DiceExprResult.getIntResult(3));
    var res2 =
        DiceExprResult.multiply(DiceExprResult.getIntResult(5), DiceExprResult.getIntResult(-3));
    var res3 =
        DiceExprResult.multiply(DiceExprResult.getIntResult(0), DiceExprResult.getIntResult(3));
    var res4 =
        DiceExprResult.multiply(DiceExprResult.getIntResult(0), DiceExprResult.getIntResult(-4));
    var res5 =
        DiceExprResult.multiply(DiceExprResult.getIntResult(-1), DiceExprResult.getIntResult(-4));

    assertSame(res1.getType(), DiceResultType.INTEGER);
    assertSame(res1.getIntResult().orElse(-999), 3);
    assertSame(res2.getType(), DiceResultType.INTEGER);
    assertSame(res2.getIntResult().orElse(-999), -15);
    assertSame(res3.getType(), DiceResultType.INTEGER);
    assertSame(res3.getIntResult().orElse(-999), 0);
    assertSame(res4.getType(), DiceResultType.INTEGER);
    assertSame(res4.getIntResult().orElse(-999), 0);
    assertSame(res5.getType(), DiceResultType.INTEGER);
    assertSame(res5.getIntResult().orElse(-999), 4);
  }

  @Test
  void multiplyIntAndDouble() {
    var res1 =
        DiceExprResult.multiply(
            DiceExprResult.getDoubleResult(1.1), DiceExprResult.getIntResult(3));
    var res2 =
        DiceExprResult.multiply(
            DiceExprResult.getIntResult(5), DiceExprResult.getDoubleResult(-3.2));
    var res3 =
        DiceExprResult.multiply(
            DiceExprResult.getIntResult(0), DiceExprResult.getDoubleResult(3.3));
    var res4 =
        DiceExprResult.multiply(
            DiceExprResult.getIntResult(0), DiceExprResult.getDoubleResult(-5.4));
    var res5 =
        DiceExprResult.multiply(
            DiceExprResult.getIntResult(-1), DiceExprResult.getDoubleResult(-5.4));

    assertSame(res1.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res1.getDoubleResult().orElse(-999), 3.3));
    assertSame(res2.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res2.getDoubleResult().orElse(-999), -16.0));
    assertSame(res3.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res3.getDoubleResult().orElse(-999), 0));
    assertSame(res4.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res4.getDoubleResult().orElse(-999), 0));
    assertSame(res5.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res5.getDoubleResult().orElse(-999), 5.4));
  }

  @Test
  void multiplyDoubles() {
    var res1 =
        DiceExprResult.multiply(
            DiceExprResult.getDoubleResult(1.1), DiceExprResult.getDoubleResult(3.2));
    var res2 =
        DiceExprResult.multiply(
            DiceExprResult.getDoubleResult(5.3), DiceExprResult.getDoubleResult(-3.2));
    var res3 =
        DiceExprResult.multiply(
            DiceExprResult.getDoubleResult(0), DiceExprResult.getDoubleResult(3.3));
    var res4 =
        DiceExprResult.multiply(
            DiceExprResult.getDoubleResult(0), DiceExprResult.getDoubleResult(-5.4));
    var res5 =
        DiceExprResult.multiply(
            DiceExprResult.getDoubleResult(-1.1), DiceExprResult.getDoubleResult(-5.4));

    assertSame(res1.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res1.getDoubleResult().orElse(-999), 3.52));
    assertSame(res2.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res2.getDoubleResult().orElse(-999), -16.96));
    assertSame(res3.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res3.getDoubleResult().orElse(-999), 0));
    assertSame(res4.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res4.getDoubleResult().orElse(-999), 0));
    assertSame(res5.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res5.getDoubleResult().orElse(-999), 5.94));
  }

  @Test
  void multiplyStrings() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            DiceExprResult.multiply(
                DiceExprResult.getStringResult("hello"), DiceExprResult.getStringResult("world")));
    assertThrows(
        IllegalArgumentException.class,
        () ->
            DiceExprResult.multiply(
                DiceExprResult.getStringResult(""), DiceExprResult.getStringResult("test")));
  }

  @Test
  void multiplyStringAndInt() {
    var res1 =
        DiceExprResult.multiply(
            DiceExprResult.getStringResult("hello"), DiceExprResult.getIntResult(3));
    var res2 =
        DiceExprResult.multiply(
            DiceExprResult.getIntResult(-4), DiceExprResult.getStringResult("test"));
    var res3 =
        DiceExprResult.multiply(
            DiceExprResult.getIntResult(4), DiceExprResult.getStringResult("test"));
    var res4 =
        DiceExprResult.multiply(
            DiceExprResult.getIntResult(0), DiceExprResult.getStringResult("test"));

    assertSame(res1.getType(), DiceResultType.STRING);
    assertEquals(res1.getStringResult(), "hellohellohello");
    assertSame(res2.getType(), DiceResultType.STRING);
    assertEquals(res2.getStringResult(), "");
    assertSame(res3.getType(), DiceResultType.STRING);
    assertEquals(res3.getStringResult(), "testtesttesttest");
    assertSame(res4.getType(), DiceResultType.STRING);
    assertEquals(res4.getStringResult(), "");
  }

  @Test
  void multiplyStringAndDouble() {
    var res1 =
        DiceExprResult.multiply(
            DiceExprResult.getStringResult("hello"), DiceExprResult.getDoubleResult(3.2));
    var res2 =
        DiceExprResult.multiply(
            DiceExprResult.getDoubleResult(-4.2), DiceExprResult.getStringResult("test"));

    assertSame(res1.getType(), DiceResultType.STRING);
    assertEquals(res1.getStringResult(), "hellohellohello");
    assertSame(res2.getType(), DiceResultType.STRING);
    assertEquals(res2.getStringResult(), "");
  }

  @Test
  void negateInts() {
    var res1 = DiceExprResult.negate(DiceExprResult.getIntResult(3));
    var res2 = DiceExprResult.negate(DiceExprResult.getIntResult(-4));
    var res3 = DiceExprResult.negate(DiceExprResult.getIntResult(0));

    assertSame(res1.getType(), DiceResultType.INTEGER);
    assertSame(res1.getIntResult().orElse(-999), -3);
    assertSame(res2.getType(), DiceResultType.INTEGER);
    assertSame(res2.getIntResult().orElse(-999), 4);
    assertSame(res3.getType(), DiceResultType.INTEGER);
    assertSame(res3.getIntResult().orElse(-999), 0);
  }

  @Test
  void negateDoubles() {
    var res1 = DiceExprResult.negate(DiceExprResult.getDoubleResult(3.4));
    var res2 = DiceExprResult.negate(DiceExprResult.getDoubleResult(-4.8));
    var res3 = DiceExprResult.negate(DiceExprResult.getDoubleResult(0));

    assertSame(res1.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res1.getDoubleResult().orElse(-999), -3.4));
    assertSame(res2.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res2.getDoubleResult().orElse(-999), 4.8));
    assertSame(res3.getType(), DiceResultType.DOUBLE);
    assertTrue(compareWithTolerance(res3.getDoubleResult().orElse(-999), 0));
  }

  @Test
  void negateString() {
    assertThrows(
        IllegalArgumentException.class,
        () -> DiceExprResult.negate(DiceExprResult.getStringResult("hello")));
  }

  @Test
  void addUndefinedAndInt() {
    assertThrows(
        IllegalArgumentException.class,
        () -> DiceExprResult.add(DiceExprResult.UNDEFINED, DiceExprResult.getIntResult(1)));

    assertThrows(
        IllegalArgumentException.class,
        () -> DiceExprResult.add(DiceExprResult.getIntResult(1), DiceExprResult.UNDEFINED));
  }

  @Test
  void addUndefinedAndDouble() {
    assertThrows(
        IllegalArgumentException.class,
        () -> DiceExprResult.add(DiceExprResult.UNDEFINED, DiceExprResult.getDoubleResult(1.3)));

    assertThrows(
        IllegalArgumentException.class,
        () -> DiceExprResult.add(DiceExprResult.getDoubleResult(4.5), DiceExprResult.UNDEFINED));
  }

  @Test
  void addUndefinedAndString() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            DiceExprResult.add(DiceExprResult.UNDEFINED, DiceExprResult.getStringResult("hello")));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            DiceExprResult.add(DiceExprResult.getStringResult("hello"), DiceExprResult.UNDEFINED));
  }

  @Test
  void subtractUndefinedAndInt() {
    assertThrows(
        IllegalArgumentException.class,
        () -> DiceExprResult.subtract(DiceExprResult.UNDEFINED, DiceExprResult.getIntResult(1)));

    assertThrows(
        IllegalArgumentException.class,
        () -> DiceExprResult.subtract(DiceExprResult.getIntResult(1), DiceExprResult.UNDEFINED));
  }

  @Test
  void subtractUndefinedAndDouble() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            DiceExprResult.subtract(DiceExprResult.UNDEFINED, DiceExprResult.getDoubleResult(1.3)));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            DiceExprResult.subtract(DiceExprResult.getDoubleResult(4.5), DiceExprResult.UNDEFINED));
  }

  @Test
  void subtractUndefinedAndString() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            DiceExprResult.subtract(
                DiceExprResult.UNDEFINED, DiceExprResult.getStringResult("hello")));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            DiceExprResult.subtract(
                DiceExprResult.getStringResult("hello"), DiceExprResult.UNDEFINED));
  }

  @Test
  void multiplyUndefinedAndInt() {
    assertThrows(
        IllegalArgumentException.class,
        () -> DiceExprResult.multiply(DiceExprResult.UNDEFINED, DiceExprResult.getIntResult(1)));

    assertThrows(
        IllegalArgumentException.class,
        () -> DiceExprResult.multiply(DiceExprResult.getIntResult(1), DiceExprResult.UNDEFINED));
  }

  @Test
  void multiplyUndefinedAndDouble() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            DiceExprResult.multiply(DiceExprResult.UNDEFINED, DiceExprResult.getDoubleResult(1.3)));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            DiceExprResult.multiply(DiceExprResult.getDoubleResult(4.5), DiceExprResult.UNDEFINED));
  }

  @Test
  void multiplyUndefinedAndString() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            DiceExprResult.multiply(
                DiceExprResult.UNDEFINED, DiceExprResult.getStringResult("hello")));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            DiceExprResult.multiply(
                DiceExprResult.getStringResult("hello"), DiceExprResult.UNDEFINED));
  }

  @Test
  void divideUndefinedAndInt() {
    assertThrows(
        IllegalArgumentException.class,
        () -> DiceExprResult.divide(DiceExprResult.UNDEFINED, DiceExprResult.getIntResult(1)));

    assertThrows(
        IllegalArgumentException.class,
        () -> DiceExprResult.divide(DiceExprResult.getIntResult(1), DiceExprResult.UNDEFINED));
  }

  @Test
  void divideUndefinedAndDouble() {
    assertThrows(
        IllegalArgumentException.class,
        () -> DiceExprResult.divide(DiceExprResult.UNDEFINED, DiceExprResult.getDoubleResult(1.3)));

    assertThrows(
        IllegalArgumentException.class,
        () -> DiceExprResult.divide(DiceExprResult.getDoubleResult(4.5), DiceExprResult.UNDEFINED));
  }

  @Test
  void divideUndefinedAndString() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            DiceExprResult.divide(
                DiceExprResult.UNDEFINED, DiceExprResult.getStringResult("hello")));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            DiceExprResult.divide(
                DiceExprResult.getStringResult("hello"), DiceExprResult.UNDEFINED));
  }

  @Test
  void negateUndefined() {
    assertThrows(
        IllegalArgumentException.class, () -> DiceExprResult.negate(DiceExprResult.UNDEFINED));
  }
}
