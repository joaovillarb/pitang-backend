package jfvb.com.pitangbackend.infrastructure.config.adapter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateSerializerAdapter extends JsonSerializer<LocalDate> {

    private final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void serialize(final LocalDate localDate, final JsonGenerator jsonGenerator,
                          final SerializerProvider serializerProvider) throws IOException {
        final String serializedLocalDateTime = dateFormatter.format(localDate);
        jsonGenerator.writeString(serializedLocalDateTime);
    }
}