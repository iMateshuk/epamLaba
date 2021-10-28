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

    SQL_GC_SELECT_W_ID("SELECT gift_certificate.*, tag.name FROM gc.tag " +
            "left join gc.gc_tag on tag.id = tag_id " +
            "left join gc.gift_certificate on gc_id = gift_certificate.id " +
            "where tag.id = ?"),

    SQL_GC_SELECT_W_NAME("SELECT gift_certificate.*, tag.name FROM gc.tag " +
            "left join  gc.gc_tag on tag.id = tag_id " +
            "left join gc.gift_certificate on gc_id = gift_certificate.id " +
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
