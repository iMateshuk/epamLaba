package com.epam.esm.dao.util;

public enum CommonSQL {

    SELECT_ID_A_NAME_W_NAME("SELECT id, name FROM gc.tag WHERE name = ?"),

    SELECT_W_GC_ID("SELECT tag.id, tag.name FROM gc.gift_certificate " +
            "left join gc.gc_tag on gift_certificate.id = gc_tag.gc_id " +
            "left join gc.tag on gc_tag.tag_id = tag.id " +
            "where gift_certificate.id = ?"),

    SELECT_W_TAG_NAME("SELECT gift_certificate.* FROM gc.tag " +
            "left join  gc.gc_tag on tag.id = gc_tag.tag_id " +
            "left join gc.gift_certificate on gc_tag.gc_id = gift_certificate.id " +
            "where tag.name = ?"),

    ;

    private String string;

    CommonSQL(String string) {
        this.string = string;
    }

    public String getSQL() {
        return string;
    }

}
