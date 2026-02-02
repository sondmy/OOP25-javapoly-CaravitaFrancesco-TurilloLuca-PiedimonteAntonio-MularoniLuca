package it.unibo.javapoly.model.impl.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.fasterxml.jackson.annotation.JsonRootName;

// TODO: add all the JavaDoc comment

@JsonRootName(value = "LandProprietyCard")
public class LandProprietyCard extends ProprietyCard {

    //final private int baseRent;
    //final private int hotelRent;
    final private List<Integer> multiProroprietyRent; // TODO: Valutare se trasformalo in una Map<Integer, Integer> -> <numHouse, numHouseRent>
    
    final private int housePrice;
    final private int hotelPrice;

    public LandProprietyCard(final String id, final String name, final String description, final int proprietyCost,
                             final String color, final int baseRent, final List<Integer> multiProroprietyRent,
                             final int hotelRent, final int houseCost, final int hotelCost) {
        super(id, name, description, proprietyCost, color);
        this.multiProroprietyRent = new ArrayList<>(multiProroprietyRent);
        this.multiProroprietyRent.addFirst(baseRent);
        this.multiProroprietyRent.addLast(hotelRent);
        this.housePrice = houseCost;
        this.hotelPrice = hotelCost;
    }

    //#region Getter

    /**
     *  This method return the base price (land rent), that a player must pay 
     * 
     * @return the base rent
     */ 
    public int getBaseRent(){

        if(checkListIsEmpty()){
            throw new NoSuchElementException("The rent list is empty");
        }
        
        return this.multiProroprietyRent.getFirst();
    }

    /**
     *  This method return the price for the hotel rent, that a player must pay 
     * 
     * @return the hotel rent
     */
    public int getHotelRent(){
        
        if(checkListIsEmpty()){
            throw new NoSuchElementException("The rent list is empty");
        }
        
        return this.multiProroprietyRent.getLast();    
    }

    /**
     * This method return the cost to built the new house on the terrain
     * 
     * @return the price to build an house
     */
    public int getHouseCost(){
        return this.housePrice;
    }

    /**
     * This method return the cost to buit the Hotel
     * 
     * @return the price to build an hotel
     */
    public int getHotelCost(){
        return this.hotelPrice;
    }

    /**
     * this method return the number of how many house are built on the land
     * 
     * @param houseNumber is the number of the house built on the land
     * @return the rent in base of the number house built
     */
    public int getNumberHouseRent(int houseNumber){

        if(checkListIsEmpty()){
            throw new NoSuchElementException("The rent list is empty");
        }

        if (checkIsHotel(houseNumber)){
            return getHotelRent();
        }

        return this.multiProroprietyRent.get(houseNumber);
    }

    /**
     * This method return all rent in base of the number of the house built
     * 
     * @return the baseRent
     */
    public List<Integer> getMultiHouseRent(){

        if(checkListIsEmpty()){
            throw new NoSuchElementException("The rent list is empty"); // TODO: Valutare se restituire un errore o semplicemente restituire una lista vuota
        }

        return new ArrayList<>(this.multiProroprietyRent);
    }

    //#endregion

    /**
     * This method calculate the rent in base of the house number or if there is any hotel
     */
    @Override
    public int calculateRent(int houseNumber) {
        return checkIsHotel(houseNumber) ? getHotelRent() : getNumberHouseRent(houseNumber);
    }

    /**
     * This method returns a string with all the data of this LandProprietyCard in JSON format
     */
    @Override
    public String toString(){
        return super.toString();
    }

    /**
     * This method checks if the passed number is not out of the List limits
     * @param number 
     * @return true if the number is out of the limit, false otherwise
     */
    private boolean checkNumberHouse(int number){

        if (number < 0 || number > this.multiProroprietyRent.size()){
            return true;
        }

        return false;
    }

        /**
     * This method checks if the passed number is ht hotel number
     * @param number 
     * @return true if the number is the hotel number, false otherwise
     */
    private boolean checkIsHotel(int number){
        if(checkNumberHouse(number)){
            throw new IndexOutOfBoundsException("The given index is out of size");
        }

        if(number == this.multiProroprietyRent.size()){
            return true;
        }

        return false;
    }

    /**
     * 
     * @return true if the this.multiProroprietyRent is a empty list, false otherwise
     */
    private boolean checkListIsEmpty(){

        if(this.multiProroprietyRent == null){
            throw new NullPointerException("The list with the rent is null");
        }

        if(this.multiProroprietyRent.isEmpty()){
            return true;
        }

        return false;
    }


    // FIXME: Capire bene cosa fare con lindece per la lista. perche bisogna vedere se vogliamo fare indice-1 o lasciare indice. 
    // Quindi bisogna vedere se vogliamo mettere baseRent nella posizione 0 del arraylist e hotelRent nell'ultima posizione


}
