package com.epam.esm.dao.util;

public enum TagSQL {

    SELECT_ALL("SELECT * FROM gc.tag"),
    SELECT_ALL_W_ID("SELECT * FROM gc.tag WHERE id = ?"),
    SELECT_ALL_W_NAME("SELECT * FROM gc.tag WHERE name = ?"),

    SELECT_COUNT_W_NAME("SELECT count(*) FROM gc.tag WHERE name = ?"),

    DEL_DB_CASCADE_W_ID("DELETE FROM gc.tag WHERE id = ?"),
    DEL_NO_CASCADE_W_ID("DELETE gc.tag, gc.gc_tag FROM gc.tag INNER JOIN gc.gc_tag ON tag.id = gc_tag.tag_id WHERE tag.id = ?"),

    INSERT_TAG("INSERT INTO gc.tag(name) VALUES(?)"),


    ;

    private String string;

    TagSQL(String string) {
        this.string = string;
    }

    public String getSQL() {
        return string;
    }
}
