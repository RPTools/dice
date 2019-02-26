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
import java.util.Collections;

public class TopLevelExpressionNode implements DiceExpressionNode {

    private final DiceExpressionNode child;
    private final String expression;

    public TopLevelExpressionNode(String expression, DiceExpressionNode child) {
        this.expression = expression;
        this.child = child;
    }

    @Override
    public DiceExprResult evaluate(DiceExpressionSymbolTable symbolTable) throws UnsupportedOperationException {
        return child.evaluate(symbolTable);
    }

    @Override
    public DiceExprResult getExprResult() {
        return child.getExprResult();
    }

    @Override
    public Collection<DiceExpressionNode> getChildren() {
        return Collections.singletonList(child);
    }

    public String getExpression() {
        return expression;
    }
}
