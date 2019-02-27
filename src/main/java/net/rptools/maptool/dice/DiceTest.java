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
package net.rptools.maptool.dice;

import net.rptools.maptool.dice.result.PlainResultFormatter;
import net.rptools.maptool.dice.symbols.DefaultDiceExpressionSymbolTable;
import net.rptools.maptool.dice.symbols.DiceEvalScope;

public class DiceTest {
    private final static DefaultDiceExpressionSymbolTable symbolTable = new DefaultDiceExpressionSymbolTable();

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.err.println("Usage: DiceTest <filename>");
            System.err.println("                  - for stdin");
        }
        DiceExpression diceExpression;
        if (args[0].equals("-")) {
            System.out.println("DiceEvalType in Script");
            diceExpression = DiceExpression.fromInputStream(System.in);
        } else {
            System.out.println("Reading Script: '" + args[0] + "'");
            diceExpression = DiceExpression.fromFile(args[0]);
        }

        diceExpression.execute(symbolTable);
        diceExpression.format(new PlainResultFormatter()).ifPresent(System.out::println);

        System.out.println("Local Variables");
        symbolTable.getVariableNames(DiceEvalScope.LOCAL).forEach(name -> {
            var val = symbolTable.getVariableValue(DiceEvalScope.LOCAL, name);
            System.out.println(val.getType() + ":" + name + " = " + val.getStringResult());
        });
        System.out.println();

        System.out.println("Global Variables");
        symbolTable.getVariableNames(DiceEvalScope.GLOBAL).forEach(name -> {
            var val = symbolTable.getVariableValue(DiceEvalScope.GLOBAL, name);
            System.out.println(val.getType() + ":" + name + " = " + val.getStringResult());
        });
        System.out.println();

        System.out.println("Property Variables");
        symbolTable.getVariableNames(DiceEvalScope.PROPERTY).forEach(name -> {
            var val = symbolTable.getVariableValue(DiceEvalScope.PROPERTY, name);
            System.out.println(val.getType() + ":" + name + " = " + val.getStringResult());
        });
        System.out.println();
    }

}
