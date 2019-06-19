package br.com.rocha.dan.task.commons.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.TimeZone;

import static java.util.Objects.nonNull;

/**
 * Created by mauriciourbanfilho on 04/10/2017.
 */
public final class ParserUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParserUtil.class);

    private ParserUtil() {
    }

    public static String convertToJSON(final Object objeto) {
        final ObjectMapper mapper = new Jackson2ObjectMapperBuilder().createXmlMapper(false).build();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JodaModule());

        try {
            return mapper.writeValueAsString(objeto);
        } catch (final JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T convertValue(final Object fromValue, final Class<T> toValueType) {

        T valueConverted = null;

        if (nonNull(fromValue)) {
            final ObjectMapper mapper = new ObjectMapper();

            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            mapper.setTimeZone(TimeZone.getTimeZone("PST"));
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            mapper.registerModule(new com.fasterxml.jackson.datatype.joda.JodaModule());

            valueConverted = mapper.convertValue(fromValue, toValueType);

        }

        return valueConverted;
    }

    public static <T> List<T> convertValues(final Object fromValues, final Class<T> toValueType) {

        List<T> convertedValues = null;

        if (nonNull(fromValues)) {

            final ObjectMapper mapper = new ObjectMapper();

            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            mapper.setTimeZone(TimeZone.getTimeZone("PST"));
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.registerModule(new com.fasterxml.jackson.datatype.joda.JodaModule());

            convertedValues = mapper.convertValue(fromValues,
                    TypeFactory.defaultInstance().constructCollectionType(List.class, toValueType));

        }

        return convertedValues;
    }

    /**
     * @param json        unico objeto json
     * @param toValueType tipo do objeto que json sera convertido
     */
    public static <T> T convertJSONToObject(final String json, final Class<T> toValueType) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JodaModule());

        T createdObject;

        try {
            createdObject = mapper.readValue(json, toValueType);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalArgumentException(e);
        }
        return createdObject;
    }

    /**
     * @param json        objeto json do tipo array
     * @param toValueType tipo do objeto que json sera convertido
     */
    public static <T> List<T> convertJSONToList(final String json, final Class<T> toValueType) {

        List<T> list = new ArrayList<>();

        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                T t = convertJSONToObject(jsonArray.get(i).toString(), toValueType);
                list.add(t);
            }
        } catch (JSONException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalArgumentException(e);
        }

        return list;
    }

    public static ObjectMapper createJsr310ObjectMapper(final JavaTimeModule jsr310) {
        return new ObjectMapper()
          .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
          .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
          .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
          .registerModule(jsr310 == null ? new JavaTimeModule() : jsr310);
    }

    public static String stringToBase64(String value) {
        try {
            return new String(Base64.getEncoder().encode(value.getBytes()));
        } catch (Exception e) {
            return "";
        }
    }

    public static String base64ToString(String value) {
        try {
            return new String(Base64.getDecoder().decode(value.getBytes()));
        } catch (Exception e) {
            return "";
        }
    }
}