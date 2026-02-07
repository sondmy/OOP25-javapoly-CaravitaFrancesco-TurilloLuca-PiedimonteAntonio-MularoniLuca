package it.unibo.javapoly.model.impl;

import it.unibo.javapoly.model.api.Player;
import it.unibo.javapoly.model.api.PlayerState;

/**
 * Represents the state of a player when they are free to move.
 * This class implements the {@link PlayerState} interface and follows the
 * Singleton pattern, ensuring that only one instance of the free state exists.
 * In this state, the player is allowed to move normally based on the dice roll
 * result.
 */
public final class FreeState implements PlayerState {

    /**
     * The single instance of the FreeState class.
     */
    private static final FreeState INSTANCE = new FreeState();

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private FreeState() {
    }

    /**
     * Returns the singleton instance of the FreeState.
     *
     * @return the single instance of {@link FreeState}.
     */
    public static FreeState getInstance() {
        return INSTANCE;
    }

    /**
     * Executes the standard turn logic for a free player.
     * The player moves to the new position indicated by the dice result.
     *
     * @param player     the player currently in this state.
     * @param diceResult the result of the dice roll.
     * @param isDouble   indicates if the dice roll was a double.
     */
    @Override
    public void playTurn(final Player player, final int diceResult, final boolean isDouble) {
        player.move(diceResult);
        System.out.println("[Stato Libero] Il giocatore si muove di " + diceResult); // NOPMD
    }

    /**
     * Checks if the player is allowed to move in this state.
     *
     * @return {@code true} as the player is in a free state and can always move.
     */
    @Override
    public boolean canMove() {
        return true;
    }
}
