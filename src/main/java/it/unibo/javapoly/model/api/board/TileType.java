package it.unibo.javapoly.model.api.board;

/**
 * Supported tile types. Extend if the board needs special types.
 */
public enum TileType {
    START,
    PROPERTY,  // FIXME: Valutare se unire tutto qui
    TAX,
    JAIL,
    GO_TO_JAIL,
    UNEXPECTED,
    FREE_PARKING,
    RAILROAD,
    UTILITY, // FIXME: Valutare se tenerlo o unirlo
    TRANSIT  // FIXME: Valutare se tenerlo o unirlo
}
