package jfvb.com.pitangbackend.infrastructure.config.adapter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateDeserializerAdapter extends JsonDeserializer<LocalDate> {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String text = p.getValueAsString();
        try {
            return LocalDate.parse(text, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

}
