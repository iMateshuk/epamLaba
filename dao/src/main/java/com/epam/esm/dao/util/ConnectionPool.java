package com.epam.esm.dao.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {

    private static ComboPooledDataSource cpds = new ComboPooledDataSource();

    private final static DBResourceManager dbResourceManager = DBResourceManager.getInstance();

    static {

        try {

            cpds.setDriverClass(dbResourceManager.getValue(DBParameter.DB_DRIVER));
            cpds.setJdbcUrl(dbResourceManager.getValue(DBParameter.DB_URL));
            cpds.setUser(dbResourceManager.getValue(DBParameter.DB_USER));
            cpds.setPassword(dbResourceManager.getValue(DBParameter.DB_PASSWORD));

        } catch (PropertyVetoException e) {

            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection() throws SQLException {

        return cpds.getConnection();
    }

    private ConnectionPool(){}
}
