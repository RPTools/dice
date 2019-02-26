/*
 * This software Copyright by the RPTools.net development team, and licensed under the Affero GPL Version 3 or, at your option, any later version.
 *
 * MapTool Source Code is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU Affero General Public License * along with this source Code. If not, please visit <http://www.gnu.org/licenses/> and specifically the Affero license text
 * at <http://www.gnu.org/licenses/agpl.html>.
 */
package net.rptools.maptool.dice.expressiontree;

import net.rptools.maptool.dice.result.DiceExprResult;
import net.rptools.maptool.dice.symbols.DiceEvalScope;
import net.rptools.maptool.dice.symbols.DiceExpressionSymbolTable;

import java.util.Collection;
import java.util.Collections;

/**
 * Represents the node used to assign a value to a symbol.
 */
public class AssignmentDiceExpressionNode implements DiceExpressionNode {

	/** The name of the symbol. */
	private final String name;

	/** The scope of the symbol. */
	private final DiceEvalScope scope;

	/** The right hand side of the assignment expression. */
	private final DiceExpressionNode rhs;

	/** The evaluated value of this node. */
	private DiceExprResult result;

	/**
	 * Creates a new node used to assign a value to a symbol.
	 *
	 * @param symbol
	 *            The name of the symbol.
	 * @param symbolScope
	 *            The scope of the symbol.
	 * @param rhs
	 *            The right hand side of the assignment.
	 */
	public AssignmentDiceExpressionNode(String symbol, DiceEvalScope symbolScope, DiceExpressionNode rhs) {
		name = symbol;
		scope = symbolScope;
		this.rhs = rhs;
	}

	@Override
	public DiceExprResult evaluate(DiceExpressionSymbolTable symbolTable) throws UnsupportedOperationException {
		result = rhs.evaluate(symbolTable);
		symbolTable.setVariableValue(scope, name, result);
		return result;
	}

	@Override
	public DiceExprResult getExprResult() {
		return result;
	}

	@Override
	public Collection<DiceExpressionNode> getChildren() {
		return Collections.singletonList(rhs);
	}

	String getName() {
		return name;
	}

	String getVariableName() {
		return getScope().getScopePrefix() + getName();
	}

	DiceEvalScope getScope() {
		return scope;
	}

}
