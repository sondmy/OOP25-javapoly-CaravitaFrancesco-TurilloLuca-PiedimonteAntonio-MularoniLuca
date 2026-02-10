package it.unibo.javapoly.controller.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.unibo.javapoly.utils.ValidationUtils;

/**
 * Data Transfer Object that represent a property's state for serialization.
 */
public class PropertySnapshot {
    private final String id;
    private final String ownerId;
    private final int houses;
    private final int purchasePrice;
    private final String group;

    /**
     * Constructor for Jackson deserialization.
     *
     * @param id the property's unique identifier.
     * @param ownerId the owner's name.
     * @param houses number of houses built on the property.
     * @param purchasePrice the property's purchase price.
     * @param group the property group name.
     */
    @JsonCreator
    public PropertySnapshot(
            @JsonProperty("id") final String id,
            @JsonProperty("ownerId") final String ownerId,
            @JsonProperty("houses") final int houses,
            @JsonProperty("purchasePrice") final int purchasePrice,
            @JsonProperty("group") final String group
    ) {
        this.id = ValidationUtils.requireNonBlank(id, "Property id cannot be blank");
        this.ownerId = ownerId;
        this.houses = ValidationUtils.requireNonNegative(houses, "Houses cannot be negative");
        this.purchasePrice = ValidationUtils.requireNonNegative(purchasePrice, "Property purchase price cannot be negative");
        this.group = ValidationUtils.requireNonNull(group, "Property group cannot be null");
    }

    /**
     * Get property id.
     *
     * @return property id.
     */
    @JsonProperty("id")
    public String getId() {
        return this.id;
    }

    /**
     * Get owner id.
     *
     * @return owner id.
     */
    @JsonProperty("ownerId")
    public String getOwnerId() {
        return this.ownerId;
    }

    /**
     * Get house count built on that property.
     *
     * @return house count built on that property.
     */
    @JsonProperty("houses")
    public int getHouses() {
        return this.houses;
    }

    /**
     * Get purchase price of the property.
     *
     * @return purchase price of the property.
     */
    @JsonProperty("purchasePrice")
    public int getPurchasePrice() {
        return this.purchasePrice;
    }

    /**
     * Get group of the property.
     *
     * @return group of the property.
     */
    @JsonProperty("group")
    public String getGroup() {
        return this.group;
    }
}
