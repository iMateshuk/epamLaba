package com.epam.esm.dao.jdbc;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Objects;

@Configuration
@PropertySource("classpath:db.properties")
public class JdbcConfig {

    @Autowired
    Environment environment;

    @Bean
    DataSource dataSource() {

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
    JdbcTemplate jdbcTemplate(DataSource dataSource) {

        return new JdbcTemplate(dataSource);
    }

    @Bean
    public TransactionManager transactionManager(DataSource dataSource) {

        return new DataSourceTransactionManager(dataSource);
    }
}
