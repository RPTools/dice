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
package net.rptools.dice.symbols;

import java.util.Set;
import net.rptools.dice.result.DiceExprResult;

/**
 * Interface implemented for symbol tables that can be used to resolve symbols for dice expression
 * evaluation.
 */
public interface DiceExpressionSymbolTable {

  /**
   * Returns the value that has been stored in a variable.
   *
   * @param scope The scope of the variable.
   * @param name The name of the variable to get the value for.
   * @return the value stored against the variable.
   */
  DiceExprResult getVariableValue(DiceEvalScope scope, String name);

  /**
   * Checks to see if the specified variable has been defined in a certain scope.
   *
   * @param scope The scope to check for teh variable.
   * @param name The name of the variable to check for.
   * @return <code>true</code> if the variable exists for the scope.
   */
  boolean containsVariable(DiceEvalScope scope, String name);

  /**
   * Sets the value for a variable in the spcified scope.
   *
   * @param scope The scope for the variable.
   * @param name The name of the variable.
   * @param value The value to set the variable to.
   */
  void setVariableValue(DiceEvalScope scope, String name, DiceExprResult value);

  /**
   * Returns the names of the variables defined for the specified scope.
   *
   * @param scope The scope to get the variables for.
   * @return a {@link Set} containing the names of the variables in the scope.
   */
  Set<String> getVariableNames(DiceEvalScope scope);
}
