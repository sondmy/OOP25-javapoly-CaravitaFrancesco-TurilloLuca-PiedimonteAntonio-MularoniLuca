package it.unibo.javapoly.model.api.player;

/**
 * Represents a token used by a player in the JavaPoly game.
 * Since this is a functional interface, it can be implemented using a lambda
 * expression to define the type of the token.
 * 
 * @see TokenType
 */
@FunctionalInterface
public interface Token {

    /**
     * Gets the type of the token.
     *
     * @return a {@link String} representing the type of the token.
     * @see TokenType
     */
    String getType();
}
