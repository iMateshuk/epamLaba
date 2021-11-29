package com.epam.esm.dao.util;

public enum TagSQL {
    SELECT_ALL("SELECT tag FROM Tag tag"),
    SELECT_ALL_BY_NAME("SELECT tag FROM Tag tag WHERE tag.name = :name"),

    ;

    private final String sql;

    TagSQL(String string) {
        this.sql = string;
    }

    public String getSQL() {
        return sql;
    }
}
