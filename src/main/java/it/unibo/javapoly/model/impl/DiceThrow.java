package it.unibo.javapoly.model.impl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.unibo.javapoly.model.api.Dice;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DiceThrow {
    private final Dice dice1;
    private final Dice dice2;

    @JsonCreator
    public DiceThrow(@JsonProperty("dice1") final Dice dice1,
                     @JsonProperty("dice2") final Dice dice2) {
        this.dice1 = dice1;
        this.dice2 = dice2;
    }

    public int throwAll() {
        dice1.throwDice();
        dice2.throwDice();
        return dice1.getDicesResult() + dice2.getDicesResult();
    }

    @JsonIgnore
    public boolean isDouble() {
        return dice1.getDicesResult() == dice2.getDicesResult();
    }
}
