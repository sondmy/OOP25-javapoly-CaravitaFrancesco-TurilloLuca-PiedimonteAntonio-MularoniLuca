package it.unibo.javapoly.model.api.card;

// TODO: add all the JavaDoc comment

/**
 * General interface that every type of card will implements ( not the ProrpietyCard type)
 */
public interface GameCard extends Card {
    // TODO: definire meglio il metodo apply in base alle esigenze del gioco aggiungendo parametri 
    void apply() ; 
}
