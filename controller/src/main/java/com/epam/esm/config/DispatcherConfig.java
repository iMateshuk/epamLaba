package com.epam.esm.config;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

@Configuration
@AllArgsConstructor
public class DispatcherConfig {
  private final MainConfigProperties configProperties;

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
    source.setBasename(configProperties.getClasspath());
    source.setUseCodeAsDefaultMessage(true);
    source.setDefaultLocale(Locale.ENGLISH);
    source.setDefaultEncoding(configProperties.getEncoding());
    source.setFallbackToSystemLocale(false);
    return source;
  }
}
