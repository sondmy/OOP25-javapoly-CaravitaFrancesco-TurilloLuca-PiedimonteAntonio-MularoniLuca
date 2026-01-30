package it.unibo.javapoly.model.impl.Card;

import it.unibo.javapoly.model.api.card.Card;

public abstract class ProprietyCard implements Card{

    final String id;
    final String name;
    final String description;
    final int proprietyCost;
    final String group;

    public ProprietyCard(final String id, final String name, final String description, final int proprietyCost, final String group) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.proprietyCost = proprietyCost;
        this.group = group;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public int getProprietyCost() {
        return this.proprietyCost;
    }

    public String getGroup() {
        return this.group;
    }

    /**
    *   this method return the final rent that a player need to pay
    *   (including house, multiplier, number of station)
    */
    public abstract int calculateRent();

    @Override
    public String toString() {
        return "ProprietyCard{" +
                "id='" + this.id + '\'' +
                ", name='" + this.name + '\'' +
                ", description='" + this.description + '\'' +
                ", proprietyCost='" + this.proprietyCost + '\'' +
                '}';
    }
    
}
