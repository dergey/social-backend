package com.sergey.zhuravlev.social.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonDateTimeConfiguration {

    private final ZoneId DEFAULT_ZONE_ID = ZoneId.of("UTC");
    private final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    @Bean
    public Module jsonMapperDateTimeModule() {
        SimpleModule bean = new SimpleModule();

        bean.addDeserializer(ZonedDateTime.class, new JsonDeserializer<>() {
            @Override
            public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                return ZonedDateTime.parse(jsonParser.getValueAsString(), DEFAULT_FORMATTER);
            }
        });

        bean.addDeserializer(LocalDateTime.class, new JsonDeserializer<>() {
            @Override
            public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                return ZonedDateTime.parse(jsonParser.getValueAsString(), DEFAULT_FORMATTER)
                    .toLocalDateTime();
            }
        });

        bean.addSerializer(ZonedDateTime.class, new JsonSerializer<>() {
            @Override
            public void serialize(
                ZonedDateTime zonedDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                throws IOException {
                jsonGenerator.writeString(DEFAULT_FORMATTER.format(zonedDateTime));
            }
        });

        bean.addSerializer(LocalDateTime.class, new JsonSerializer<>() {
            @Override
            public void serialize(
                LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                throws IOException {
                jsonGenerator.writeString(DEFAULT_FORMATTER.format(localDateTime.atZone(DEFAULT_ZONE_ID)));
            }
        });

        return bean;
    }

}
