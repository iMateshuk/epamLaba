package com.epam.esm;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.epam.esm")
public class CertificateApp {
  private static final String profile = "dev";

  public static void main(String[] args) {

    new SpringApplicationBuilder(CertificateApp.class)
        .profiles(profile)
        .run(args);
  }
}
