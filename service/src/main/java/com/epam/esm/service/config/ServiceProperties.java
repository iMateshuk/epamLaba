package com.epam.esm.service.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@PropertySource("classpath:jwt.properties")
@ConfigurationProperties(prefix = "srv")
@Validated
public class ServiceProperties {
  @NotNull
  private String jwtSecret;
}
