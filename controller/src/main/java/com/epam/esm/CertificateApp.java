package com.epam.esm;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class CertificateApp {

  public static void main(String[] args) {

    new SpringApplicationBuilder(CertificateApp.class)
        .run(args);
  }

  @PostConstruct
  public void started() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }
}
