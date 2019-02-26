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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceRollerArgumentTest {

    @Test
    void getArgumentName() {
        assertEquals(new DiceRollerArgument("test").getArgumentName(), "test");
        assertEquals(new DiceRollerArgument("test 2").getArgumentName(), "test 2");
        assertEquals(new DiceRollerArgument("cs").getArgumentName(), "cs");
        assertEquals(new DiceRollerArgument("cf").getArgumentName(), "cf");

        assertEquals(new DiceRollerArgument("test", 4).getArgumentName(), "test");
        assertEquals(new DiceRollerArgument("test 2", 9).getArgumentName(), "test 2");
        assertEquals(new DiceRollerArgument("cs", 20).getArgumentName(), "cs");
        assertEquals(new DiceRollerArgument("cf", 10).getArgumentName(), "cf");

        assertEquals(new DiceRollerArgument("test", "=", 4).getArgumentName(), "test");
        assertEquals(new DiceRollerArgument("test 2", "<", 9).getArgumentName(), "test 2");
        assertEquals(new DiceRollerArgument("cs", "=", 20).getArgumentName(), "cs");
        assertEquals(new DiceRollerArgument("cf", ">", 10).getArgumentName(), "cf");
    }

    @Test
    void getOperator() {
        assertTrue(new DiceRollerArgument("test").getOperator().isEmpty());
        assertTrue(new DiceRollerArgument("test 2").getOperator().isEmpty());
        assertTrue(new DiceRollerArgument("cs").getOperator().isEmpty());
        assertTrue(new DiceRollerArgument("cf").getOperator().isEmpty());

        assertTrue(new DiceRollerArgument("test", 4).getOperator().isEmpty());
        assertTrue(new DiceRollerArgument("test 2", 9).getOperator().isEmpty());
        assertTrue(new DiceRollerArgument("cs", 20).getOperator().isEmpty());
        assertTrue(new DiceRollerArgument("cf", 10).getOperator().isEmpty());

        assertEquals(new DiceRollerArgument("test", "=", 4).getArgumentName(), "test");
        assertEquals(new DiceRollerArgument("test 2", "<", 9).getArgumentName(), "test 2");
        assertEquals(new DiceRollerArgument("cs", "=", 20).getArgumentName(), "cs");
        assertEquals(new DiceRollerArgument("cf", ">", 10).getArgumentName(), "cf");
    }

    @Test
    void getNumber() {
        assertTrue(new DiceRollerArgument("test").getNumber().isEmpty());
        assertTrue(new DiceRollerArgument("test 2").getNumber().isEmpty());
        assertTrue(new DiceRollerArgument("cs").getNumber().isEmpty());
        assertTrue(new DiceRollerArgument("cf").getNumber().isEmpty());

        assertEquals(new DiceRollerArgument("test", 4).getNumber().orElse(-999), 4);
        assertEquals(new DiceRollerArgument("test 2", 9).getNumber().orElse(-999), 9);
        assertEquals(new DiceRollerArgument("cs", 20).getNumber().orElse(-999), 20);
        assertEquals(new DiceRollerArgument("cf", 10).getNumber().orElse(-999), 10);

        assertEquals(new DiceRollerArgument("test", "=", 4).getNumber().orElse(-999), 4);
        assertEquals(new DiceRollerArgument("test 2", "<", 9).getNumber().orElse(-999), 9);
        assertEquals(new DiceRollerArgument("cs", "=", 20).getNumber().orElse(-999), 20);
        assertEquals(new DiceRollerArgument("cf", ">", 10).getNumber().orElse(-999), 10);
    }

    @Test
    void useDefaultNumber() {
        assertTrue(new DiceRollerArgument("test").useDefaultNumber());
        assertTrue(new DiceRollerArgument("test 2").useDefaultNumber());
        assertTrue(new DiceRollerArgument("cs").useDefaultNumber());
        assertTrue(new DiceRollerArgument("cf").useDefaultNumber());

        assertFalse(new DiceRollerArgument("test", 4).useDefaultNumber());
        assertFalse(new DiceRollerArgument("test 2", 9).useDefaultNumber());
        assertFalse(new DiceRollerArgument("cs", 20).useDefaultNumber());
        assertFalse(new DiceRollerArgument("cf", 10).useDefaultNumber());

        assertFalse(new DiceRollerArgument("test", "=", 4).useDefaultNumber());
        assertFalse(new DiceRollerArgument("test 2", "<", 9).useDefaultNumber());
        assertFalse(new DiceRollerArgument("cs", "=", 20).useDefaultNumber());
        assertFalse(new DiceRollerArgument("cf", ">", 10).useDefaultNumber());
    }

    @Test
    void toStr() {
        assertEquals(new DiceRollerArgument("test").toString(), "test");
        assertEquals(new DiceRollerArgument("test 2").toString(), "test 2");
        assertEquals(new DiceRollerArgument("cs").toString(), "cs");
        assertEquals(new DiceRollerArgument("cf").toString(), "cf");

        assertEquals(new DiceRollerArgument("test", 4).toString(), "test = 4");
        assertEquals(new DiceRollerArgument("test 2", 9).toString(), "test 2 = 9");
        assertEquals(new DiceRollerArgument("cs", 20).toString(), "cs = 20");
        assertEquals(new DiceRollerArgument("cf", 10).toString(), "cf = 10");

        assertEquals(new DiceRollerArgument("test", "=", 4).toString(), "test = 4");
        assertEquals(new DiceRollerArgument("test 2", "<", 9).toString(), "test 2 < 9");
        assertEquals(new DiceRollerArgument("cs", "=", 20).toString(), "cs = 20");
        assertEquals(new DiceRollerArgument("cf", ">", 10).toString(), "cf > 10");
    }
}