package it.unibo.javapoly.model.impl;

import it.unibo.javapoly.model.api.Dice;

public class DiceThrow {
    private final Dice dice1;
    private final Dice dice2;
    private int lastDiceResult;

    public DiceThrow(final Dice dice1, final Dice dice2) {
        this.dice1 = dice1;
        this.dice2 = dice2;
        lastDiceResult = 0;
    }

    public void throwAll() {
        dice1.throwDice();
        dice2.throwDice();
        lastDiceResult = dice1.getDicesResult() + dice2.getDicesResult();
    }

    public int getLastThrow(){
        return this.lastDiceResult;
    }

    public boolean isDouble() {
        return dice1.getDicesResult() == dice2.getDicesResult();
    }
}
