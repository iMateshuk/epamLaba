package com.epam.esm.dao.config;

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
@PropertySource("classpath:db.properties")
@ConfigurationProperties(prefix = "db")
public class DaoConfigProperties {
  private String driver;
  private String url;
  private String user;
  private String password;
  private int initialPoolSize;
  private int minPoolSize;
  private int maxPoolSize;
  private int maxIdleTime;

  private String packagesToScan;
  private String showSql;
  private String ddlAuto;
  private String nameStrategy;

  private String embUrl;
  private String embDev;
  private String embFill;
}
