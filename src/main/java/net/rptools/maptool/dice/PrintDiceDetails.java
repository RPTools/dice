package net.rptools.maptool.dice;

public class PrintDiceDetails extends DiceExprBaseListener {

    @Override
    public void enterIntegerVal(DiceExprParser.IntegerValContext ctx) {
        System.out.println("Integer: " + ctx.getText());
    }

    @Override
    public void enterDoubleVal(DiceExprParser.DoubleValContext ctx) {
        System.out.println("Double: " + ctx.getText());
    }
}
