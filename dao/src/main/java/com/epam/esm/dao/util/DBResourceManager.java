package com.epam.esm.dao.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class DBResourceManager {

    private static final String DB_FILENAME = "db";

    private static final DBResourceManager INSTANCE = new DBResourceManager();

    private ResourceBundle bundle = ResourceBundle.getBundle(DB_FILENAME, Locale.ENGLISH);

    private DBResourceManager() {}

    public static DBResourceManager getInstance() {

        return INSTANCE;
    }

    public String getValue(String key) {

        return bundle.getString(key);
    }
}
