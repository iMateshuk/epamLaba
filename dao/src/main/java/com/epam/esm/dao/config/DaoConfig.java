package com.epam.esm.dao.config;

import com.epam.esm.dao.util.GiftCertificateSQL;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.lang.NonNull;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
public class DaoConfig {
  @NonNull
  @Value("${jdbc.driver}")
  private String driver;
  @NonNull
  @Value("${jdbc.url}")
  private String url;
  @NonNull
  @Value("${jdbc.user}")
  private String user;
  @NonNull
  @Value("${jdbc.password}")
  private String password;
  @Value("${connection.pool.initialPoolSize:5}")
  private int initialPoolSize;
  @Value("${connection.pool.minPoolSize:5}")
  private int minPoolSize;
  @Value("${connection.pool.maxPoolSize:20}")
  private int maxPoolSize;
  @Value("${connection.pool.maxIdleTime:3000}")
  private int maxIdleTime;

  @NonNull
  @Value("${hibernate.packagesToScan}")
  private String packagesToScan;
  @NonNull
  @Value("${hibernate.show_sql}")
  private String showSql;
  @NonNull
  @Value("${hibernate.ddl-auto}")
  private String ddlAuto;
  @NonNull
  @Value("${hibernate.naming-strategy}")
  private String nameStrategy;

  @Bean
  @Profile("prod")
  public DataSource dataSource() {
    ComboPooledDataSource dataSource = new ComboPooledDataSource();
    try {
      dataSource.setDriverClass(driver);
      dataSource.setJdbcUrl(url);
      dataSource.setUser(user);
      dataSource.setPassword(password);

      dataSource.setInitialPoolSize(initialPoolSize);
      dataSource.setMinPoolSize(minPoolSize);
      dataSource.setMaxPoolSize(maxPoolSize);
      dataSource.setMaxIdleTime(maxIdleTime);

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
        .setName("test;MODE=MySQL;IGNORECASE=TRUE;DATABASE_TO_UPPER=false;INIT=CREATE SCHEMA IF NOT EXISTS gc")
        .addScript("classpath:sql/gc-dev.sql")
        .addScript("classpath:sql/fill-gc.sql")
        .build();
  }

  @Bean
  @Profile("prod")
  public Map<GiftCertificateSQL, String> giftCertificateProd() {
    Map<GiftCertificateSQL, String> giftCertificateSQLs = new HashMap<>();

    giftCertificateSQLs.put(GiftCertificateSQL.INSERT_GIFT_CERT, GiftCertificateSQL.INSERT_GIFT_CERT.getSQL());
    giftCertificateSQLs.put(GiftCertificateSQL.UPDATE_DATA_IF_NOT_NULL_EMPTY, GiftCertificateSQL.UPDATE_DATA_IF_NOT_NULL_EMPTY.getSQL());
    return giftCertificateSQLs;
  }

  @Bean
  @Profile("dev")
  public Map<GiftCertificateSQL, String> giftCertificateDev() {
    Map<GiftCertificateSQL, String> giftCertificateSQLs = new HashMap<>();

    giftCertificateSQLs.put(GiftCertificateSQL.INSERT_GIFT_CERT, GiftCertificateSQL.INSERT_GIFT_CERT_TST.getSQL());
    giftCertificateSQLs.put(GiftCertificateSQL.UPDATE_DATA_IF_NOT_NULL_EMPTY, GiftCertificateSQL.UPDATE_DATA_IF_NOT_NULL_EMPTY_TST.getSQL());
    return giftCertificateSQLs;
  }

  @Bean
  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Bean
  public GiftCertificateMapper giftCertificateMapper() {
    return new GiftCertificateMapper();
  }

  @Bean
  public TagMapper tagMapper() {
    return new TagMapper();
  }

  @Bean
  public TransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

  @Bean
  public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
    Properties properties = new Properties();
    properties.put("hibernate.show_sql", showSql);
    properties.put("hibernate.ddl-auto", ddlAuto);
    properties.put("hibernate.naming-strategy", nameStrategy);

    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    factory.setPackagesToScan(packagesToScan);
    factory.setDataSource(dataSource);
    factory.setJpaProperties(properties);
    factory.afterPropertiesSet();

    return factory.getObject();
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory(dataSource()));
    return transactionManager;
  }

/*  @Bean
  public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
    Properties properties = new Properties();
    properties.put("hibernate.show_sql", showSql);

    LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
    emf.setDataSource(dataSource);
    emf.setPackagesToScan(packagesToScan);
    emf.setJpaProperties(properties);
    emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    return emf.getObject();
  }

  @Bean
  public SessionFactory sessionFactoryProvider(EntityManagerFactory entityManagerFactory) {
    return entityManagerFactory.unwrap(SessionFactory.class);
  }

  @Bean
  public JpaTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory);
    return transactionManager;
  }*/
}
