package it.unibo.javapoly.model.impl.Card;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;

// TODO: add all the JavaDoc comment

@JsonRootName(value = "LandProprietyCard")
public class LandProprietyCard extends ProprietyCard {
    final private int baseRent;
    final private int hotelRent;
    final private List<Integer> multiProroprietyCost;
    
    final private int housePrice;
    final private int hotelPrice;

    public LandProprietyCard(final String id, final String name, final String description, final int proprietyCost,
                             final String color, final int baseRent, final List<Integer> multiProroprietyCost,
                             final int hotelRent, final int houseCost, final int hotelCost) {
        super(id, name, description, proprietyCost, color);
        this.baseRent = baseRent;
        this.multiProroprietyCost = new ArrayList<>(multiProroprietyCost);
        this.hotelRent = hotelRent;
        this.housePrice = houseCost;
        this.hotelPrice = hotelCost;
    }

    @Override
    public int calculateRent() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String toString(){
        return super.toString();
    }
    // @Override
    // public String toString(){
    //     return super.toString() +
    //             ", housePrice:'" + this.housePrice + '\'' +
    //             ", hotelPrice:'" + this.hotelPrice + '\'' +
    //             ", rents: '" + " { " + 
    //             " baseRent: " +
    //             '}';
    // }
}
