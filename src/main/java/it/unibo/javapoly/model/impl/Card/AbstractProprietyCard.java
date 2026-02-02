package it.unibo.javapoly.model.impl.card;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;

import it.unibo.javapoly.model.api.card.Card;
import it.unibo.javapoly.utils.JsonUtils;


/**
 * Base representation of a property card.
 *
 * <p>
 * Concrete implementations represent the different kinds of properties
 * (streets, stations, utilities). This class contains basic common fields
 * and behaviour shared by all property cards.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = LandProprietyCard.class, name = "street"),
    @JsonSubTypes.Type(value = StationPropetyCard.class, name = "station"),
    @JsonSubTypes.Type(value = UtilityProprietyCard.class, name = "utility")
})
@JsonRootName("ProprietyCard")
public abstract class AbstractProprietyCard implements Card {

    private final String id;
    private final String name;
    private final String description;
    private final int proprietyCost;
    private final String group;

    /**
     * Creates a new property card.
     *
     * @param id the card identifier
     * @param name the card name
     * @param description the card description
     * @param proprietyCost the purchase cost of the property
     * @param group the color / group of the property
     */
    public AbstractProprietyCard(final String id,
                         final String name,
                         final String description,
                         final int proprietyCost,
                         final String group) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.proprietyCost = proprietyCost;
        this.group = group;
    }

    /**
     * {@inheritDoc}
     *
     * @return the unique identifier of the card.
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * {@inheritDoc}
     *
     * @return the display name of the card.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     *
     * @return the description of the card.
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the purchase cost of this property.
     *
     * @return the property cost.
     */
    public int getProprietyCost() {
        return this.proprietyCost;
    }

    /**
     * Returns the group (color set) to which this property belongs.
     *
     * @return the property group.
     */
    public String getGroup() {
        return this.group;
    }

    /**
     * Returns the final rent that a player needs to pay for this property,
     * depending on the provided parameter (e.g. number of houses, or other
     * modifiers specific to the concrete property type).
     *
     * @param number a parameter whose meaning depends on the concrete
     *               implementation (for example: number of houses)
     * @return the calculated rent for the given parameter.
     */
    public abstract int calculateRent(int number);

    /**
     * Returns a JSON representation of this card.
     *
     * @return the JSON string representing this card; if serialization
     *         fails a short JSON error object is returned.
     */
    @Override
    public String toString() {     // FIXME: Model can't use utils
        try {
            return JsonUtils.mapper().writeValueAsString(this);
        } catch (final JsonProcessingException e) {
            return "{\"error\":\"Serialization failed\"}";
        }
    }
}
