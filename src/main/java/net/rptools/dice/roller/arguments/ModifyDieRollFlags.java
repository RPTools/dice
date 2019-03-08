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
package net.rptools.dice.roller.arguments;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import net.rptools.dice.result.DiceExprResult;
import net.rptools.dice.result.DieRoll;

class ModifyDieRollFlags {
  private final List<DieRoll.DieRollFlags> flags;
  private final List<DieRoll.DieRollFlags> mutuallyExclusive;

  ModifyDieRollFlags(
      Collection<DieRoll.DieRollFlags> toSet, Collection<DieRoll.DieRollFlags> exclusive) {
    flags = List.copyOf(toSet);
    mutuallyExclusive = List.copyOf(exclusive);
  }

  ModifyDieRollFlags(DieRoll.DieRollFlags toSet, DieRoll.DieRollFlags exclusive) {
    this(Collections.singleton(toSet), Collections.singleton(exclusive));
  }

  DieRoll performTest(DieRoll roll, String operator, DiceExprResult val,  int defaultVal) {
    boolean matches = false;
    int testVal;

    if (val == DiceExprResult.UNDEFINED) {
      testVal = defaultVal;
    } else {
      testVal = val.getIntResult().orElse(defaultVal);
    }

    switch (operator) {
      case "=":
        if (roll.getValue() == testVal) {
          matches = true;
        }
        break;
      case "<":
        if (roll.getValue() < testVal) {
          matches = true;
        }
        break;
      case ">":
        if (roll.getValue() > testVal) {
          matches = true;
        }
        break;
      case "<=":
        if (roll.getValue() <= testVal) {
          matches = true;
        }
        break;
      case ">=":
        if (roll.getValue() >= testVal) {
          matches = true;
        }
        break;
      default:
        throw new IllegalArgumentException("Unknown operator " + operator);
    }

    if (matches) {
      List<DieRoll.DieRollFlags> newFlags = new LinkedList<>(roll.getFlags());
      newFlags.removeAll(mutuallyExclusive);
      newFlags.addAll(flags);
      return roll.withNewFlags(newFlags);
    } else {
      return roll;
    }
  }
}
