package com.epam.esm.dao.util;

public enum TagSQL {
    QL_SELECT_ALL("SELECT tag FROM Tag tag"),
    QL_SELECT_ALL_W_NAME("SELECT tag FROM Tag tag WHERE tag.name = :name"),

    ;

    private final String sql;

    TagSQL(String string) {
        this.sql = string;
    }

    public String getSQL() {
        return sql;
    }
}
