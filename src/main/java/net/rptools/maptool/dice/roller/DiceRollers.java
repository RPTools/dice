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

import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class registers all the {@link DiceRoller} subclasses that have been annotated with {@link DiceRollerDefinition}.
 */
public class DiceRollers {

    /** The singleton instance of this class. */
    private final static DiceRollers instance = new DiceRollers();

    /** The registered {@link DiceRoller}s */
    private final Map<String, DiceRoller> diceRollers = new HashMap<>();

    /**
     * Returns the singleton instance of this class.
     *
     * @return the singleton instance of this class.
     */
    public static DiceRollers getInstance() {
        return instance;
    }

    /**
     * Constructor for the class. This method will search for all the annotated {@link DiceRoller} classes and register them.
     */
    private DiceRollers() {
        Reflections reflections = new Reflections("net.rptools.maptool.dice");

        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(DiceRollerDefinition.class);
        classes.forEach(cl -> {
            try {
                Object obj = cl.getDeclaredConstructor(new Class[0]).newInstance();
                if (obj instanceof DiceRoller) {
                    DiceRollerDefinition def = cl.getAnnotation(DiceRollerDefinition.class);
                    registerDiceRoller(def.patterns(), (DiceRoller) obj);
                } else {
                    // TODO: Change to logging
                    System.err.println("Unable to register DiceRoller " + cl.getName() + " as it is wrong type.");
                }
            } catch (Exception e) {
                // TODO: Change to logging
                System.err.println("Unable to register DiceRoller " + cl.getName());
            }
        });
        // TODO: Change to logging
        System.out.println("Registerd " + diceRollers.size() + " patterns for dice rollers");
    }

    /**
     * Registers a {@link DiceRoller} against some patters.
     *
     * @param patterns
     *            The patterns that the {@link DiceRoller} should be registered for.
     * @param roller
     *            The {@link DiceRoller} that should be registered.
     */
    private void registerDiceRoller(String[] patterns, DiceRoller roller) {
        for (String pattern : patterns) {
            diceRollers.put(pattern, roller);
        }
    }

    /**
     * Returns the registered {@link DiceRoller} for the specified pattern.
     *
     * @param pattern
     *            The pattern to get the {@link DiceRoller} for.
     * @return The {@link DiceRoller} for the pattern.
     */
    public DiceRoller getDiceRoller(String pattern) {
        return diceRollers.get(pattern);
    }

}
