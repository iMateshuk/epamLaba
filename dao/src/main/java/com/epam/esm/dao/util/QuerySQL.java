package com.epam.esm.dao.util;

public enum QuerySQL {

    SQL_GC_COLUMN_ID("id"),
    SQL_GC_COLUMN_NAME("name"),
    SQL_GC_COLUMN_DESC("description"),
    SQL_GC_COLUMN_PRICE("price"),
    SQL_GC_COLUMN_DUR("duration"),
    SQL_GC_COLUMN_CD("create_date"),
    SQL_GC_COLUMN_LUD("last_update_date"),

    SQL_GC_SELECT_ALL("SELECT * FROM gc.gift_certificate"),
    SQL_TAG_SELECT_ALL("SELECT * FROM gc.tag"),

    SQL_TAG_SELECT_ID_A_NAME_W_NAME("SELECT id, name FROM gc.tag WHERE name = ?"),

    SQL_TAG_SELECT_W_GC_ID("SELECT tag.id, tag.name FROM gc.gift_certificate " +
            "left join gc.gc_tag on gift_certificate.id = gc_tag.gc_id " +
            "left join gc.tag on gc_tag.tag_id = tag.id " +
            "where gift_certificate.id = ?"),

    SQL_GC_SELECT_W_TAG_NAME("SELECT gift_certificate.* FROM gc.tag " +
            "left join  gc.gc_tag on tag.id = gc_tag.tag_id " +
            "left join gc.gift_certificate on gc_tag.gc_id = gift_certificate.id " +
            "where tag.name = ?"),

    ;

    private String string;

    QuerySQL(String string) {
        this.string = string;
    }

    public String getSQL() {
        return string;
    }

}
