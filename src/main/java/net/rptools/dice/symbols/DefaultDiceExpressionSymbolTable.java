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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import net.rptools.dice.result.DiceExprResult;

/** Default symbol table for variable resolution during evaluating dice expressions. */
public class DefaultDiceExpressionSymbolTable implements DiceExpressionSymbolTable {

  /** The variables that have been set and their scopes. */
  private final Map<DiceEvalScope, Map<String, DiceExprResult>> variables = new HashMap<>();

  /** Creates a new {@link DefaultDiceExpressionSymbolTable}. */
  public DefaultDiceExpressionSymbolTable() {
    DiceEvalScope[] scopes = DiceEvalScope.values();
    for (DiceEvalScope scope : scopes) {
      variables.put(scope, new HashMap<>());
    }
  }

  @Override
  public DiceExprResult getVariableValue(DiceEvalScope scope, String name) {
    return variables.get(scope).get(name);
  }

  @Override
  public boolean containsVariable(DiceEvalScope scope, String name) {
    return variables.get(scope).containsKey(name);
  }

  @Override
  public void setVariableValue(DiceEvalScope scope, String name, DiceExprResult value) {
    variables.get(scope).put(name, value);
  }

  @Override
  public Set<String> getVariableNames(DiceEvalScope scope) {
    return variables.get(scope).keySet();
  }
}
