package it.unibo.javapoly.model.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import it.unibo.javapoly.model.impl.DiceImpl;

/**
 * Interface representing a single die or a set of dice.
 */
@JsonDeserialize(as = DiceImpl.class)
public interface Dice {

    /**
     * Throws the dice to generate a new random result.
     */
    void throwDice();

    /**
     * Returns the result of the last dice throw.
     *
     * @return the sum of the dice results.
     */
    int getDicesResult();
}
