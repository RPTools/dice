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
package net.rptools.maptool.dice.roller;

import static org.junit.jupiter.api.Assertions.*;

import net.rptools.maptool.dice.result.DiceExprResult;
import net.rptools.maptool.dice.result.DiceResultType;
import net.rptools.maptool.dice.roller.arguments.UnknownDiceRollerArgument;
import org.junit.jupiter.api.Test;

class UnknownDiceRollerArgumentTest {

  @Test
  void getArgumentName() {
    assertEquals(new UnknownDiceRollerArgument("test", 1).getArgumentName(), "test");
    assertEquals(new UnknownDiceRollerArgument("test 2", 1).getArgumentName(), "test 2");
    assertEquals(new UnknownDiceRollerArgument("cs", 1).getArgumentName(), "cs");
    assertEquals(new UnknownDiceRollerArgument("cf", 1).getArgumentName(), "cf");

    assertEquals(
        new UnknownDiceRollerArgument("test", "=", DiceExprResult.getIntResult(4), 1)
            .getArgumentName(),
        "test");
    assertEquals(
        new UnknownDiceRollerArgument("test 2", "<", DiceExprResult.getIntResult(9), 1)
            .getArgumentName(),
        "test 2");
    assertEquals(
        new UnknownDiceRollerArgument("cs", "=", DiceExprResult.getIntResult(20), 1)
            .getArgumentName(),
        "cs");
    assertEquals(
        new UnknownDiceRollerArgument("cf", ">", DiceExprResult.getIntResult(10), 1)
            .getArgumentName(),
        "cf");
  }

  @Test
  void getOperator() {
    assertTrue(new UnknownDiceRollerArgument("test", 1).getOperator().isEmpty());
    assertTrue(new UnknownDiceRollerArgument("test 2", 1).getOperator().isEmpty());
    assertTrue(new UnknownDiceRollerArgument("cs", 1).getOperator().isEmpty());
    assertTrue(new UnknownDiceRollerArgument("cf", 1).getOperator().isEmpty());

    assertEquals(
        new UnknownDiceRollerArgument("test", "=", DiceExprResult.getIntResult(4), 1)
            .getArgumentName(),
        "test");
    assertEquals(
        new UnknownDiceRollerArgument("test 2", "<", DiceExprResult.getIntResult(9), 1)
            .getArgumentName(),
        "test 2");
    assertEquals(
        new UnknownDiceRollerArgument("cs", "=", DiceExprResult.getIntResult(20), 1)
            .getArgumentName(),
        "cs");
    assertEquals(
        new UnknownDiceRollerArgument("cf", ">", DiceExprResult.getIntResult(10), 1)
            .getArgumentName(),
        "cf");
  }

  @Test
  void getNumber() {
    assertEquals(
        new UnknownDiceRollerArgument("test", 1).getValue().getType(), DiceResultType.UNDEFINED);
    assertEquals(
        new UnknownDiceRollerArgument("test 2", 1).getValue().getType(), DiceResultType.UNDEFINED);
    assertEquals(
        new UnknownDiceRollerArgument("cs", 1).getValue().getType(), DiceResultType.UNDEFINED);
    assertEquals(
        new UnknownDiceRollerArgument("cf", 1).getValue().getType(), DiceResultType.UNDEFINED);

    assertEquals(
        new UnknownDiceRollerArgument("test", "=", DiceExprResult.getIntResult(4), 1)
            .getValue()
            .getIntResult()
            .orElse(-999),
        4);
    assertEquals(
        new UnknownDiceRollerArgument("test 2", "<", DiceExprResult.getIntResult(9), 1)
            .getValue()
            .getIntResult()
            .orElse(-999),
        9);
    assertEquals(
        new UnknownDiceRollerArgument("cs", "=", DiceExprResult.getIntResult(20), 1)
            .getValue()
            .getIntResult()
            .orElse(-999),
        20);
    assertEquals(
        new UnknownDiceRollerArgument("cf", ">", DiceExprResult.getIntResult(10), 1)
            .getValue()
            .getIntResult()
            .orElse(-999),
        10);
  }
}
