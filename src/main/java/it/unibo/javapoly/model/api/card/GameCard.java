package it.unibo.javapoly.model.api.card;

// TODO: add all the JavaDoc comment

/**
 * General interface that every type of card will implement
 * (not the {@code ProprietyCard} type).
 */
public interface GameCard extends Card {

    // TODO: definire meglio il metodo apply in base alle esigenze del gioco aggiungendo parametri

    /**
     * Applies the effect of the card to the game state.
     */
    void apply();
}
