package com.epam.esm;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CertificateApp {

  public static void main(String[] args) {

    new SpringApplicationBuilder(CertificateApp.class)
        .profiles("prod")
        .run(args);
  }
}
