package net.rptools.maptool.dice;

import net.rptools.maptool.dice.symbols.DefaultDiceExpressionSymbolTable;
import net.rptools.maptool.dice.symbols.DiceEvalScope;


public class DiceTest {

    private final static DefaultDiceExpressionSymbolTable symbolTable = new DefaultDiceExpressionSymbolTable();

    public static void  main(String[] args) throws Exception {

        if (args.length == 0) {
            System.err.println("Usage: DiceTest <filename>");
            System.err.println("                  - for stdin");
        }


        DiceExpression diceExpression;

        if (args[0].equals("-")) {
            System.out.println("DiceEvalType in Script");
            diceExpression = DiceExpression.fromInputStream(System.in);
        } else {
            System.out.println("Reading Script: '" + args[0] +"'");
            diceExpression = DiceExpression.fromFile(args[0]);
        }



        /*System.out.println();
        System.out.println();
        System.out.println(tree.toStringTree(parser));
        System.out.println();*/


        diceExpression.execute(symbolTable);


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
