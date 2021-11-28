package com.epam.esm.dao.util;

public enum TagSQL {
    QL_SELECT_ALL("SELECT tag FROM Tag tag"),
    QL_SELECT_ALL_W_NAME("SELECT tag FROM Tag tag WHERE tag.name = :name"),
    QL_INSERT_TAG("INSERT INTO Tag(name) VALUES(:name)"),
    QL_SELECT_W_GC_ID("SELECT tag.id, tag.name FROM Certificate cert" +
        "left join gc_tag on cert.id = gc_tag.gc_id " +
        "left join tag on gc_tag.tag_id = tag.id " +
        "where cert.id = :id"),

    ;

    private String sql;

    TagSQL(String string) {
        this.sql = string;
    }

    public String getSQL() {
        return sql;
    }
}
