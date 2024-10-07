package com.davidnguyen.backend.config;

import dev.akkinoc.util.YamlResourceBundle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ResourceBundle;

@Slf4j
@Configuration
public class ResourceBundleConfig {
    @Bean
    public ResourceBundle resourceBundle() {
        return ResourceBundle.getBundle("i18n/messages", YamlResourceBundle.Control.INSTANCE);
    }
}
