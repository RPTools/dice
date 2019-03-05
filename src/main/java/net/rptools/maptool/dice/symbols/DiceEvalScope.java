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
package net.rptools.maptool.dice.symbols;

/** This enum represents the scopes for the dice expression evaluator symbol tables.. */
public enum DiceEvalScope {

  /** Local scope, soon forgotten. */
  LOCAL("local", "$"),
  /** Global scope, used for updating/reading flags. */
  GLOBAL("global", "#"),
  /** Property scope, used for reading the property of the current token. */
  PROPERTY("property", "@");

  /** The name of the scope. */
  private final String scopeName;

  /** The prefix of the scope. */
  private final String scopePrefix;

  /**
   * Creates a new <code>DiceEvalScope</code> enumerated value.
   *
   * @param name The name of the scope.
   * @param prefix The prefix for the scope.
   */
  DiceEvalScope(String name, String prefix) {
    scopeName = name;
    scopePrefix = prefix;
  };

  /**
   * Returns the name of the scope.
   *
   * @return the name of the scope.
   */
  public String getScopeName() {
    return scopeName;
  }

  /**
   * Returns the prefix of the scope.
   *
   * @return the profix of the scope.
   */
  public String getScopePrefix() {
    return scopePrefix;
  }

  /**
   * Returns the enum value for the specified prefix.
   *
   * @param scopePrefix The prefix to get the enumerated type value for.
   *     <p>If the prefix is not valid then an {@link IllegalArgumentException} will be thrown.
   * @return the enumerated type value for the prefix.
   */
  public static DiceEvalScope getScopeForPrefix(String scopePrefix) {
    for (var scope : values()) {
      if (scope.getScopePrefix().equals(scopePrefix)) {
        return scope;
      }
    }

    throw new IllegalArgumentException("Unknown scope prefix " + scopePrefix);
  }
}
