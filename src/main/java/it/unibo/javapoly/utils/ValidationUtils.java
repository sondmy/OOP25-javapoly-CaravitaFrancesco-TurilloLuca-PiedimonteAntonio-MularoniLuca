package it.unibo.javapoly.utils;

import java.util.Collection;
import java.util.Objects;

/**
 * Utility class for common validation checks.
 *
 * This class provides static methods to validate arguments and throw
 * appropriate exceptions if the validation fails. It cannot be instantiated.
 */
public final class ValidationUtils {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private ValidationUtils() {
    }

    // --- Null Checks ---

    /**
     * Checks that the specified object reference is not {@code null}.
     *
     * @param <T> the type of the reference.
     * @param obj the object reference to check for nullity.
     * @return {@code obj} if not {@code null}.
     * @throws NullPointerException if {@code obj} is {@code null}.
     */
    public static <T> T requireNonNull(T obj) {
        return Objects.requireNonNull(obj, "The object cannot be null");
    }

    /**
     * Checks that the specified object reference is not {@code null}.
     *
     * @param <T>          the type of the reference.
     * @param obj          the object reference to check for nullity.
     * @param errorMessage the detail message for the exception.
     * @return {@code obj} if not {@code null}.
     * @throws NullPointerException if {@code obj} is {@code null}.
     */
    public static <T> T requireNonNull(T obj, String errorMessage) {
        return Objects.requireNonNull(obj, errorMessage);
    }

    // --- String Checks (Not Null And Not Empty) ---

    /**
     * Checks that the string is not {@code null}, not empty, and not only
     * whitespace.
     *
     * @param input the string to check.
     * @return the input string if valid.
     * @throws IllegalArgumentException if the string is {@code null}, empty, or
     *                                  blank.
     */
    public static String requireNonBlank(String input) {
        return requireNonBlank(input, "The string cannot be null or blank");
    }

    /**
     * Checks that the string is not {@code null}, not empty, and not only
     * whitespace.
     *
     * @param input        the string to check.
     * @param errorMessage the detail message for the exception.
     * @return the input string if valid.
     * @throws IllegalArgumentException if the string is {@code null}, empty, or
     *                                  blank.
     */
    public static String requireNonBlank(String input, String errorMessage) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(errorMessage);
        }
        return input;
    }

    // --- Numbers Non-Negative Checks (>= 0) ---

    /**
     * Checks that the integer value is non-negative (>= 0).
     *
     * @param value the value to check.
     * @return the value if non-negative.
     * @throws IllegalArgumentException if the value is negative.
     */
    public static int requireNonNegative(int value) {
        return requireNonNegative(value, "The value cannot be negative: " + value);
    }

    /**
     * Checks that the integer value is non-negative (>= 0).
     *
     * @param value        the value to check.
     * @param errorMessage the detail message for the exception.
     * @return the value if non-negative.
     * @throws IllegalArgumentException if the value is negative.
     */
    public static int requireNonNegative(int value, String errorMessage) {
        if (value < 0) {
            throw new IllegalArgumentException(errorMessage);
        }
        return value;
    }

    // --- Numbers Strictly Positive Checks (> 0) ---

    /**
     * Checks that the int value is strictly positive (> 0).
     *
     * @param value the value to check.
     * @return the value if strictly positive.
     * @throws IllegalArgumentException if the value is not positive (<= 0).
     */
    public static int requirePositive(int value) {
        return requirePositive(value, "The value must be positive: " + value);
    }

    /**
     * Checks that the int value is strictly positive (> 0).
     *
     * @param value        the value to check.
     * @param errorMessage the detail message for the exception.
     * @return the value if strictly positive.
     * @throws IllegalArgumentException if the value is not positive (<= 0).
     */
    public static int requirePositive(int value, String errorMessage) {
        if (value <= 0) {
            throw new IllegalArgumentException(errorMessage);
        }
        return value;
    }

    // --- Range Checks (< min, > max) ---

    /**
     * Checks that the int value is within the specified range [min, max]
     * (inclusive).
     *
     * @param value the value to check.
     * @param min   the minimum allowed value.
     * @param max   the maximum allowed value.
     * @return the value if within range.
     * @throws IllegalArgumentException if the value is outside the range.
     */
    public static int requireRange(int value, int min, int max) {
        return requireRange(value, min, max,
                "Value out of allowed range: " + value + " Range: " + min + "-" + max);
    }

    /**
     * Checks that the int value is within the specified range [min, max]
     * (inclusive).
     *
     * @param value        the value to check.
     * @param min          the minimum allowed value.
     * @param max          the maximum allowed value.
     * @param errorMessage the detail message for the exception.
     * @return the value if within range.
     * @throws IllegalArgumentException if the value is outside the range.
     */
    public static int requireRange(int value, int min, int max, String errorMessage) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(errorMessage);
        }
        return value;
    }

    // --- Collection Index Checks (< 0, >= size) ---

    /**
     * Checks that the index is valid for the given collection (0 <= index < size).
     *
     * @param index      the index to check.
     * @param collection the collection to check against.
     * @return the index if valid.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public static int requireValidIndex(int index, Collection<?> collection) {
        return requireValidIndex(index, collection,
                "Index out of bounds: " + index + " for collection of size " + collection.size());
    }

    /**
     * Checks that the index is valid for the given collection (0 <= index < size).
     *
     * @param index        the index to check.
     * @param collection   the collection to check against.
     * @param errorMessage the detail message for the exception.
     * @return the index if valid.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public static int requireValidIndex(int index, Collection<?> collection, String errorMessage) {
        if (index < 0 || index >= collection.size()) {
            throw new IndexOutOfBoundsException(errorMessage);
        }
        return index;
    }
}
