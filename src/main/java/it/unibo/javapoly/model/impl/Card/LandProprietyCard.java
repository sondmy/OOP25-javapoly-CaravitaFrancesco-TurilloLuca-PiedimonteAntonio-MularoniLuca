package it.unibo.javapoly.model.impl.Card;

import java.util.List;

public class LandProprietyCard extends ProprietyCard{
    final int baseRent;
    final int hotelRent;
    final List<Integer> multiProroprietyCost;
    

    final int houseCost;
    final int hotelCost;

    public LandProprietyCard(final String id, final String name, final String description, final int proprietyCost,
                             final String color, final int baseRent, final List<Integer> multiProroprietyCost,
                             final int hotelRent, final int houseCost, final int hotelCost) {
        super(id, name, description, proprietyCost, color);
        this.baseRent = baseRent;
        this.multiProroprietyCost = multiProroprietyCost;
        this.hotelRent = hotelRent;
        this.houseCost = houseCost;
        this.hotelCost = hotelCost;
    }

    @Override
    public int calculateRent() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
