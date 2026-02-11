package it.unibo.javapoly.controller.impl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unibo.javapoly.controller.api.MatchController;
import it.unibo.javapoly.utils.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for serializing and deserializing MatchControllerImpl instances to and from JSON files.
 */
public final class MatchControllerSerializer {
    private MatchControllerSerializer() {
    }

    /**
     * Deserializes a MatchControllerImpl from the given JSON file.
     *
     * @param file the JSON file containing the serialized MatchControllerImpl.
     * @return the deserialized MatchControllerImpl instance.
     * @throws Exception if an error occurs during deserialization or file reading.
     */
    public static MatchControllerImpl deserialize(final File file) throws Exception {
        final ObjectMapper mapper = JsonUtils.getInstance().mapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        final JsonNode root = mapper.readTree(file);
        final JsonNode matchNode = root.get("MarchControllerImpl");
        if (matchNode == null) {
            throw new IllegalArgumentException("Invalid JSON: missing 'MarchControllerImpl' field");
        }
        return mapper.treeToValue(matchNode, MatchControllerImpl.class);
    }

    /**
     * Serializes the given MatchController to a JSON string and writes it to the specified file.
     *
     * @param matchController the MatchController to serialize.
     * @param file the file to write the JSON string to.
     * @return json string.
     * @throws Exception if an error occurs during serialization or file writing.
     */
    public static String serialize(final MatchController matchController, final File file) throws IOException {
        final ObjectMapper mapper = JsonUtils.getInstance().mapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        final Map<String, Object> wrapper = new HashMap<>();
        wrapper.put("MarchControllerImpl", matchController);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(wrapper);
    }
}
