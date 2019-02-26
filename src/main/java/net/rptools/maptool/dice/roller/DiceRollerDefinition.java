/*
 * This software Copyright by the RPTools.net development team, and licensed under the Affero GPL Version 3 or, at your option, any later version.
 *
 * MapTool Source Code is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU Affero General Public License * along with this source Code. If not, please visit <http://www.gnu.org/licenses/> and specifically the Affero license text
 * at <http://www.gnu.org/licenses/agpl.html>.
 */
package net.rptools.maptool.dice.roller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
/**
 * This is used to annotate classes that implement {@DiceRoller} so that they will be registered with the parser.
 */
public @interface DiceRollerDefinition {

	/**
	 * The name of the {@link DiceRoller} that this annotation is for.
	 *
	 * @return the name of the {@link DiceRoller}.
	 */
	String name();

	/**
	 * The letters that represent the pattern for the {@DiceRoller}.
	 *
	 * @return the letters that represent the {@DiceRoller}.
	 */
	String[] patterns() default {};

	/**
	 * The description of {@DiceRoller}.
	 *
	 * @return the description of the {@DiceRoller}.
	 */
	String description();
}
