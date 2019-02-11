package net.rptools.maptool.dice;

import net.rptools.maptool.dice.symbols.DefaultDiceExpressionSymbolTable;
import net.rptools.maptool.dice.symbols.DiceEvalScope;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.FileInputStream;

public class DiceTest {

    public static void  main(String[] args) throws Exception {

        if (args.length == 0) {
            System.err.println("Usage: DiceTest <filename>");
            System.err.println("                  - for stdin");
        }


        // Create a CharStream  that reads from standard input
        ANTLRInputStream input;
        if (args[0].equals("-")) {
            System.out.println("DiceEvalType in Script");
            input = new ANTLRInputStream(System.in);
        } else {
            System.out.println("Reading Script: '" + args[0] +"'");
            input = new ANTLRInputStream(new FileInputStream(new File(args[0])));
        }

        // Create a lexer that feeds off of input CharStream
        DiceExprLexer lexer = new DiceExprLexer(input);

        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // create a parser that feeds off the tokens buffer
        DiceExprParser parser = new DiceExprParser(tokens);


        ParseTree tree = parser.diceRolls();
        System.out.println();
        System.out.println();
        System.out.println(tree.toStringTree(parser));
        System.out.println();

        // Create a generic parse tree walker that can trigger callbacks
        ParseTreeWalker walker = new ParseTreeWalker();
        // Walk the tree created during the parse, trigger callbacks
//        walker.walk(new PrintDiceDetails(), tree);
        var visitor = new DiceRollVisitor(new DefaultDiceExpressionSymbolTable());
        visitor.visit(tree);
        System.out.println();
        System.out.println();

        System.out.println("Local Variables");
        visitor.getSymbolTable().getVariableNames(DiceEvalScope.LOCAL).forEach(name -> {
                var val = visitor.getSymbolTable().getVariableValue(DiceEvalScope.LOCAL, name);
                System.out.println(val.getType() + ":" + name + " = " + val.getStringResult());
        });
        System.out.println();


        System.out.println("Global Variables");
        visitor.getSymbolTable().getVariableNames(DiceEvalScope.GLOBAL).forEach(name -> {
            var val = visitor.getSymbolTable().getVariableValue(DiceEvalScope.GLOBAL, name);
            System.out.println(val.getType() + ":" + name + " = " + val.getStringResult());
        });
        System.out.println();


        System.out.println("Property Variables");
        visitor.getSymbolTable().getVariableNames(DiceEvalScope.PROPERTY).forEach(name -> {
            var val = visitor.getSymbolTable().getVariableValue(DiceEvalScope.PROPERTY, name);
            System.out.println(val.getType() + ":" + name + " = " + val.getStringResult());
        });
        System.out.println();

        //printAST(((MTScriptParser.ScriptContext) tree).getRuleContext());

        System.out.println();
        System.out.println();

        for (DiceExprResultOld res : visitor.getResults()) {
            System.out.println(res);
        }
    }


    public static void printAST(RuleContext ctx) {
        explore(ctx, 0);
    }

    public static void explore(RuleContext ctx, int indentation) {
        //String ruleName = MTScriptParser.ruleNames[ctx.getRuleIndex()];
        for (int i =0; i < indentation; i++) {
            System.out.print(" ");
        }
        //System.out.println('*' + ruleName);
        for (int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree element = ctx.getChild(i);
            if (element instanceof RuleContext) {
                explore((RuleContext) element, indentation + 4);
            } else {
                for (int x =0; x < indentation + 4; x++) {
                    System.out.print(" ");
                }
                System.out.println('-' + element.toString());
            }
        }
    }
}
