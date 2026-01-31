package it.unibo.javapoly.utils;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude;




public final class JsonUtils {
    private static final ObjectMapper MAPPER = create();

    private JsonUtils() {}

    private static ObjectMapper create() {
        ObjectMapper m = new ObjectMapper();
        m.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        m.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        m.enable(SerializationFeature.INDENT_OUTPUT); // per toString leggibile
        m.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        return m;
    }

    /**
     * This method return the mapper of Jackson library. 
     * in this way every classes use the same object. 
     * This ObjectMapper its used for Serialization and deserialization of json item.
     */
    public static ObjectMapper mapper() {
        return MAPPER.copy();
    }
}
