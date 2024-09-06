package com.davidnguyen.backend.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SpringConfig {
    @Bean
    public ModelMapper modelMapperBean() {
        return new ModelMapper();
    }
}