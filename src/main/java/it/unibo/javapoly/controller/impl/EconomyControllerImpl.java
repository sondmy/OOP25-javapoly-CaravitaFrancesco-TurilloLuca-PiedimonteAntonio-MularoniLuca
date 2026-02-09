package it.unibo.javapoly.controller.impl;

import it.unibo.javapoly.controller.api.EconomyController;
import it.unibo.javapoly.model.api.Player;
import it.unibo.javapoly.model.api.economy.Transaction;
import it.unibo.javapoly.model.api.property.Property;


import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the EconomyController interface.
 */
public class EconomyControllerImpl implements EconomyController {

    private final List<Transaction> transactionHistory;

    /**
     * Creates an EconomyController with the given list of properties.
     *
     * @param properties the list of all properties in the game
     */
    public EconomyControllerImpl() {
        this.transactionHistory = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Transaction> getTransactions() {
        return new ArrayList<>(this.transactionHistory);
    }

    @Override
    public void depositToPlayer(Player player, int amount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'depositToPlayer'");
    }

    @Override
    public boolean withdrawFromPlayer(Player player, int amount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'withdrawFromPlayer'");
    }

    @Override
    public boolean purchaseProperty(Player buyer, Property property) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'purchaseProperty'");
    }

    @Override
    public boolean purchaseHouse(Player owner, Property property) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'purchaseHouse'");
    }

    @Override
    public boolean sellHouse(Player owner, Property property) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sellHouse'");
    }

    @Override
    public boolean sellProperty(Player owner, Property property) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sellProperty'");
    }

    @Override
    public boolean payRent(Player payer, String owner, Property property, int diceRoll) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'payRent'");
    }
}
