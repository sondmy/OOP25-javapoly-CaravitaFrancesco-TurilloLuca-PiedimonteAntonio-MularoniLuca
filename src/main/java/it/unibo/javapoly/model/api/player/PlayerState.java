package it.unibo.javapoly.model.api.player;

/**
 * Represents the state of a player in the game.
 * This interface defines the behavior of a player based on their current status
 * It follows the State design pattern.
 * The player's state can be one of the following:
 * <ul>
 * <li><strong>In Jail:</strong> The player is currently in jail and has
 * specific rules governing their actions, such as attempting to roll doubles to
 * get out or paying a fine.</li>
 * <li><strong>Free:</strong> The player is not in jail and can move freely
 * around the board, manage their properties, and interact with other
 * players.</li>
 * <li><strong>Bankrupt:</strong> The player has lost all their money and is out
 * of the game. They cannot take any actions and are effectively removed from
 * play.</li>
 * </ul>
 * The {@link PlayerState} interface defines the methods that govern the
 * player's actions during their turn, ensuring that the correct behavior is
 * executed based on their current state.
 */
public interface PlayerState {

    /**
     * Executes the logic for the player's turn based on the dice roll.
     * The behavior of the player during their turn is determined by their current
     * state, such as being in jail, free, or bankrupt.
     * The method handles the player's actions, including moving the player and
     * updating their state as necessary.
     * The logic for the turn is delegated to the current {@link PlayerState} of the
     * player, ensuring that the correct behavior is executed based on the player's
     * situation.
     *
     * @param player     the player performing the turn.
     * @param diceResult the total value obtained from rolling the dice.
     * @param isDouble   indicates if the dice roll was a double.
     */
    void playTurn(Player player, int diceResult, boolean isDouble);

    /**
     * checks if the player is allowed to move from their current position.
     *
     * @return true if the player can move, false otherwise.
     */
    boolean canMove();

}
