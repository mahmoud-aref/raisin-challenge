package com.raisin.backend.infrastructure.http.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Decoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class FeignConfiguration {

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        return builder.createXmlMapper(true).build();
    }

    @Bean // custom decoder to handle abnormal responses (not parsable)
    public Decoder feignDecoder() {
        return new FixtureDecoder();
    }


}
