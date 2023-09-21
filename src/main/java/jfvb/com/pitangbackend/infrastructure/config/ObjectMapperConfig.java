package jfvb.com.pitangbackend.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jfvb.com.pitangbackend.infrastructure.config.adapter.LocalDateSerializerAdapter;
import jfvb.com.pitangbackend.infrastructure.config.adapter.LocalDateDeserializerAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class ObjectMapperConfig {

    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        final JavaTimeModule javaTimeModule = new JavaTimeModule();

        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializerAdapter());
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializerAdapter());

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(javaTimeModule);

        return mapper;
    }

}
