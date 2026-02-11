package it.unibo.javapoly.model.impl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import it.unibo.javapoly.model.api.PlayerState;

/**
 * Utility class for creating PlayerState instances from class names.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public final class PlayerStateFactory {

    private PlayerStateFactory() {
        // Prevent instantiation
    }

    /**
     * Creates a PlayerState instance from a class name.
     *
     * @param className the fully qualified class name or simple class name.
     * @return the corresponding PlayerState instance.
     * @throws IllegalArgumentException if the class name is not recognized.
     */
    public static PlayerState createFromClassName(final String className) {
        return createFromClassName(className, null);
    }

    /**
     * Creates a PlayerState instance from a class name with optional turns in jail.
     *
     * @param className the fully qualified class name or simple class name.
     * @param turnsInJail the number of turns in jail (only used for JailedState, can be null).
     * @return the corresponding PlayerState instance.
     * @throws IllegalArgumentException if the class name is not recognized.
     */
    public static PlayerState createFromClassName(final String className, final Integer turnsInJail) {
        if (className == null || className.isEmpty()) {
            return FreeState.getInstance();
        }

        // Extract simple class name if fully qualified name is provided
        final String simpleName = className.contains(".")
                ? className.substring(className.lastIndexOf('.') + 1)
                : className;

        switch (simpleName) {
            case "FreeState":
                return FreeState.getInstance();
            case "JailedState":
                return turnsInJail != null ? new JailedState(turnsInJail) : new JailedState();
            case "BankruptState":
                return BankruptState.getInstance();
            default:
                throw new IllegalArgumentException("Unknown PlayerState class: " + className);
        }
    }

    /**
     * Gets the class name from a PlayerState instance.
     *
     * @param state the PlayerState instance.
     * @return the simple class name.
     */
    public static String getClassName(final PlayerState state) {
        return state.getClass().getSimpleName();
    }

    /**
     * Gets the turns in jail from a JailedState, or null for other states.
     *
     * @param state the PlayerState instance.
     * @return the number of turns in jail, or null if not jailed.
     */
    public static Integer getTurnsInJail(final PlayerState state) {
        if (state instanceof JailedState) {
            return ((JailedState) state).getTurnsInJail();
        }
        return null;
    }
}
