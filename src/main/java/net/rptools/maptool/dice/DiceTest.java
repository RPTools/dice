package net.rptools.maptool.dice;

import net.rptools.maptool.dice.result.DiceResultNode;
import net.rptools.maptool.dice.result.DiceRollVisitor;
import net.rptools.maptool.dice.symbols.DefaultDiceExpressionSymbolTable;
import net.rptools.maptool.dice.symbols.DiceEvalScope;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import org.antlr.v4.runtime.tree.ParseTreeWalker;


public class DiceTest {

    private final static DefaultDiceExpressionSymbolTable symbolTable = new DefaultDiceExpressionSymbolTable();

    public static void  main(String[] args) throws Exception {

        if (args.length == 0) {
            System.err.println("Usage: DiceTest <filename>");
            System.err.println("                  - for stdin");
        }


        // Create a CharStream  that reads from standard input
        CharStream input;
        if (args[0].equals("-")) {
            System.out.println("DiceEvalType in Script");
            input = CharStreams.fromStream(System.in);
        } else {
            System.out.println("Reading Script: '" + args[0] +"'");
            input = CharStreams.fromFileName(args[0]);
        }

        // Create a lexer that feeds off of input CharStream
        DiceExprLexer lexer = new DiceExprLexer(input);

        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // create a parser that feeds off the tokens buffer
        DiceExprParser parser = new DiceExprParser(tokens);


        ParseTree tree = parser.diceRolls();
        /*System.out.println();
        System.out.println();
        System.out.println(tree.toStringTree(parser));
        System.out.println();*/

        // Create a generic parse tree walker that can trigger callbacks
        ParseTreeWalker walker = new ParseTreeWalker();
        // Walk the tree created during the parse, trigger callbacks
//        walker.walk(new PrintDiceDetails(), tree);
        var visitor = new DiceRollVisitor();
        visitor.visit(tree);
        /*System.out.println();
        System.out.println();


        //printAST(((MTScriptParser.ScriptContext) tree).getRuleContext());

        System.out.println();
        System.out.println();*/

        for (DiceResultNode node : visitor.getResults()) {
            node.evaluate(symbolTable);
            //System.out.println(node.getFormattedText());
        }


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
