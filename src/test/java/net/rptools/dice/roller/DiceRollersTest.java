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

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

class DiceRollersTest {

  @Test
  void registeredDiceRollers() {
    DiceRollers diceRollers = DiceRollers.getInstance();

    Reflections reflections = new Reflections("net.rptools.maptool.dice");

    Set<Class<?>> classes = reflections.getTypesAnnotatedWith(DiceRollerDefinition.class);
    classes.forEach(
        cl -> {
          try {
            Object obj = cl.getDeclaredConstructor(new Class[0]).newInstance();
            if (obj instanceof DiceRoller) {
              DiceRollerDefinition def = cl.getAnnotation(DiceRollerDefinition.class);
              for (var p : def.patterns()) {
                assertEquals(diceRollers.getDiceRoller(p).getClass(), obj.getClass());
              }
            } else {
              System.err.println(
                  "Unable to register DiceRoller " + cl.getName() + " as it is wrong type.");
            }
          } catch (Exception e) {
            System.err.println("Unable to register DiceRoller " + cl.getName());
          }
        });
  }

  @Test
  void getDiceRoller() {}
}
