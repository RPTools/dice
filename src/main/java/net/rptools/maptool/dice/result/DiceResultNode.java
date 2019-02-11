package net.rptools.maptool.dice.result;

import net.rptools.maptool.dice.InvalidExprOperation;

public interface DiceResultNode {

    DiceExprResult getExprResult();

    String getFormatedText();

    void addChild(DiceResultNode child)  throws InvalidExprOperation;

}
