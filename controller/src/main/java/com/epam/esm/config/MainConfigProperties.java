package com.epam.esm.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "main")
public class MainConfigProperties {
  private String classpath;
  private String encoding;
}