package com.epam.esm.dao.jdbc;

import com.epam.esm.dao.util.GiftCertificateSQL;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
public class JdbcConfig {

    private final Environment environment;

    public JdbcConfig(Environment environment) {

        this.environment = environment;
    }

    @Bean
    @Profile("prod")
    public DataSource dataSource() {

        final String DRIVER = "driver";
        final String URL = "url";
        final String USER = "user";
        final String PASSWORD = "password";
        final String POOL_SIZE = "poolsize";

        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        try {

            dataSource.setDriverClass(environment.getProperty(DRIVER));
            dataSource.setJdbcUrl(environment.getProperty(URL));
            dataSource.setUser(environment.getProperty(USER));
            dataSource.setPassword(environment.getProperty(PASSWORD));
            dataSource.setInitialPoolSize(Integer.parseInt(Objects.requireNonNull(environment.getProperty(POOL_SIZE))));

        } catch (PropertyVetoException e) {

            throw new ExceptionInInitializerError(e);
        }

        return dataSource;
    }

    @Bean
    @Profile("dev")
    public DataSource embeddedDataSource(){

        System.out.println();

        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("test;MODE=MySQL;IGNORECASE=TRUE;DATABASE_TO_UPPER=false;INIT=CREATE SCHEMA IF NOT EXISTS gc")
                .addScript("classpath:sql/gc-dev.sql")
                .addScript("classpath:sql/fill-gc.sql")
                .build();
    }

    @Bean
    @Profile("prod")
    public Map<GiftCertificateSQL, String> createGiftCertificateProd() {

        Map<GiftCertificateSQL, String> giftCertificateSQLs = new HashMap<>();

        giftCertificateSQLs.put(GiftCertificateSQL.INSERT_GIFT_CERT, GiftCertificateSQL.INSERT_GIFT_CERT.getSQL());
        giftCertificateSQLs.put(GiftCertificateSQL.UPDATE_DATA_IF_NOT_NULL_EMPTY, GiftCertificateSQL.UPDATE_DATA_IF_NOT_NULL_EMPTY.getSQL());

        return giftCertificateSQLs;
    }

    @Bean
    @Profile("dev")
    public Map<GiftCertificateSQL, String> createGiftCertificateDev() {

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
    public TransactionManager transactionManager(DataSource dataSource) {

        return new DataSourceTransactionManager(dataSource);
    }
}
