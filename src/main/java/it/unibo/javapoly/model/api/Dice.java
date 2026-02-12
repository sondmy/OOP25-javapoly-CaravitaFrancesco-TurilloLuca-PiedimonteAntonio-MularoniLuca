package it.unibo.javapoly.model.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import it.unibo.javapoly.model.impl.DiceImpl;

@JsonDeserialize(as = DiceImpl.class)
public interface Dice {
    public void throwDice();
    public int getDicesResult();
}
