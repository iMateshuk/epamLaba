package com.epam.esm.service.config;

import com.epam.esm.service.util.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    @Bean
    public Validator validator(){
        return new Validator();
    }
}
