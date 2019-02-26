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

import net.rptools.maptool.dice.result.DiceExprResult;

/**
 * Interface implemented by all dice rollers that are used.
 */
public interface DiceRoller {
    /**
     * Roll the dice and return the result as a {@link DiceExprResult}.
     *
     * @param pattern
     *            The dice pattern that was used.
     * @param numDice
     *            The number of dice to roll.
     * @param numSides
     *            The number of sides for the dice.
     * @return a {@link DiceExprResult} containing the result and details of the dice roll.
     */
    DiceExprResult roll(String pattern, int numDice, int numSides);
}
