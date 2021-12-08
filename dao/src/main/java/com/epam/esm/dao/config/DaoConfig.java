package com.epam.esm.dao.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@AllArgsConstructor
@EnableJpaRepositories
@EnableJpaAuditing
public class DaoConfig {
  private final DaoConfigProperties configProperties;

  @Bean
  @Profile("prod")
  public DataSource dataSource() {
    ComboPooledDataSource dataSource = new ComboPooledDataSource();
    try {
      dataSource.setDriverClass(configProperties.getDriver());
      dataSource.setJdbcUrl(configProperties.getUrl());
      dataSource.setUser(configProperties.getUser());
      dataSource.setPassword(configProperties.getPassword());

      dataSource.setInitialPoolSize(configProperties.getInitialPoolSize());
      dataSource.setMinPoolSize(configProperties.getMinPoolSize());
      dataSource.setMaxPoolSize(configProperties.getMaxPoolSize());
      dataSource.setMaxIdleTime(configProperties.getMaxIdleTime());

    } catch (PropertyVetoException e) {
      throw new ExceptionInInitializerError(e);
    }
    return dataSource;
  }

  @Bean
  @Profile("dev")
  public DataSource embeddedDataSource() {
    return new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.H2)
        .setName(configProperties.getEmbUrl())
        .addScript(configProperties.getEmbDev())
        .addScript(configProperties.getEmbFill())
        .build();
  }

  @Bean
  public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
    Properties properties = new Properties();
    properties.put("hibernate.show_sql", configProperties.getShowSql());
    properties.put("hibernate.ddl-auto", configProperties.getDdlAuto());
    properties.put("hibernate.naming-strategy", configProperties.getNameStrategy());

    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    factory.setPackagesToScan(configProperties.getPackagesToScan());
    factory.setDataSource(dataSource);
    factory.setJpaProperties(properties);
    factory.afterPropertiesSet();

    return factory.getObject();
  }

  @Bean
  public PlatformTransactionManager transactionManager(DataSource dataSource) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory(dataSource));
    return transactionManager;
  }
}