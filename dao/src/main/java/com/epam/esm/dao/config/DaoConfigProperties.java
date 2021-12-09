package com.epam.esm.dao.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@PropertySource("classpath:db.properties")
@ConfigurationProperties(prefix = "db")
@Validated
public class DaoConfigProperties {
  @NotNull
  private String driver;
  @NotNull
  private String url;
  @NotNull
  private String user;
  @NotNull
  private String password;
  @Min(5)
  @Max(100)
  private int initialPoolSize;
  @Min(5)
  @Max(100)
  private int minPoolSize;
  @Min(20)
  @Max(100)
  private int maxPoolSize;
  @Min(3000)
  @Max(5000)
  private int maxIdleTime;

  @NotNull
  private String packagesToScan;
  @NotNull
  private String showSql;
  @NotNull
  private String ddlAuto;
  @NotNull
  private String nameStrategy;

  @NotNull
  private String embUrl;
  @NotNull
  private String embDev;
  @NotNull
  private String embFill;
}
