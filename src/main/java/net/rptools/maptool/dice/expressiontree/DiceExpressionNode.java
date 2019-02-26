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
package net.rptools.maptool.dice.expressiontree;

import net.rptools.maptool.dice.result.DiceExprResult;
import net.rptools.maptool.dice.symbols.DiceExpressionSymbolTable;

import java.util.Collection;

/**
 * Nodes used to represent different roll expressions during parsing and execution.
 */
public interface DiceExpressionNode {

    /**
     * Evaluate the node.
     *
     * @param symbolTable
     *            The symbol table used to resolve symbols.
     *
     * @return The evaluated value of the expression.
     *
     * @throws UnsupportedOperationException
     *             if the operation attempting to be performed is invalid.
     */
    DiceExprResult evaluate(DiceExpressionSymbolTable symbolTable) throws UnsupportedOperationException;

    /**
     * Returns the result of the evaluation, this must always remain the same
     *
     * @return the result of the evaluation.
     */
    DiceExprResult getExprResult();

    /**
     * Returns the children of this node.
     *
     * @return the children of this code.
     */
    Collection<DiceExpressionNode> getChildren();

}
