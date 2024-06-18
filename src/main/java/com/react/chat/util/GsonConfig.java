package com.react.chat.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GsonConfig {

    @Bean
    public Gson gson() {
        JsonSerializer<LocalDateTime> serializer = (src, typeOfSrc, context) ->
                context.serialize(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        JsonDeserializer<LocalDateTime> deserializer = (json, typeOfT, context) ->
                LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, serializer)
                .registerTypeAdapter(LocalDateTime.class, deserializer)
                .create();
    }
}
