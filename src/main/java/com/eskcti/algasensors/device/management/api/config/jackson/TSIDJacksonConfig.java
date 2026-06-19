package com.eskcti.algasensors.device.management.api.config.jackson;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilderCustomizer;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;

import io.hypersistence.tsid.TSID;

@Configuration
public class TSIDJacksonConfig {

    @Bean
    public Module tsidModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(TSID.class, new TSIDToStringSerializer());
        module.addDeserializer(TSID.class, new TSIDToStringDeserializer());
        return module;
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer tsidCustomizer() {
        return builder -> builder.modulesToInstall(tsidModule());
    }
}
