package it.unibo.javapoly.model.api.economy;

import it.unibo.javapoly.model.player.Player;

import java.util.Optional;

/**
 * Immutable record representing a financial transaction in the game.
 *
 * @param id the unique identifier for this transaction.
 * @param type the type of transaction.
 * @param payer the player performing the payment (empty if bank).
 * @param payee the player receiving funds (empty if bank).
 * @param propertyId the property involved in the transaction (empty if none).
 * @param amount the monetary amount of the transaction.
 */
public record Transaction(
        int id,
        TransactionType type,
        Optional<Player> payer,
        Optional<Player> payee,
        Optional<String> propertyId,
        int amount
) {

}
