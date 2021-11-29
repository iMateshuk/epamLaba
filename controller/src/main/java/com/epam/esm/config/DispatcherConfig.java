package com.epam.esm.config;

import com.epam.esm.exception.ExceptionUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.lang.NonNull;

import java.util.Locale;

@Configuration
public class DispatcherConfig {
  @NonNull
  @Value("${dispatcher.classpath}")
  private String classpath;

  @NonNull
  @Value("${dispatcher.encoding}")
  private String encoding;

  @NonNull
  @Value("${dispatcher.context.path}")
  private String path;

  @NonNull
  @Value("${dispatcher.server.port}")
  private Integer serverPort;

  @Bean
  public ExceptionUtil exceptionUtil(MessageSource messageSource){
    return new ExceptionUtil(messageSource);
  }

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
    source.setBasename(classpath);
    source.setUseCodeAsDefaultMessage(true);
    source.setDefaultLocale(Locale.ENGLISH);
    source.setDefaultEncoding(encoding);
    source.setFallbackToSystemLocale(false);
    return source;
  }

  @Bean
  public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
    return factory -> {
      factory.setContextPath(path);
      factory.setPort(serverPort);
    };
  }
}
