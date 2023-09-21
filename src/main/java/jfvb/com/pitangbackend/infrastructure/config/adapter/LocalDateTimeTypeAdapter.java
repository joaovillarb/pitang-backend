package jfvb.com.pitangbackend.infrastructure.config.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeTypeAdapter extends TypeAdapter<LocalDate> {

    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void write(JsonWriter out, LocalDate value) throws IOException {
        out.value(formatter.format(value));
    }

    public LocalDate read(JsonReader in) throws IOException {
        String value = in.nextString();
        return LocalDate.parse(value, formatter);
    }
}
