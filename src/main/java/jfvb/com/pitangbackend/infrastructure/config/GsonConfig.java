package jfvb.com.pitangbackend.infrastructure.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jfvb.com.pitangbackend.infrastructure.config.adapter.LocalDateTimeTypeAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class GsonConfig {

    @Bean
    @ConditionalOnMissingBean(Gson.class)
    public Gson customGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTimeTypeAdapter())
                .create();
    }
}
