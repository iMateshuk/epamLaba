package com.epam.esm.dao.util;

public enum GiftCertificateSQL {

    SQL_GC_COLUMN_ID("id"),
    SQL_GC_COLUMN_NAME("name"),
    SQL_GC_COLUMN_DESC("description"),
    SQL_GC_COLUMN_PRICE("price"),
    SQL_GC_COLUMN_DUR("duration"),
    SQL_GC_COLUMN_CD("create_date"),
    SQL_GC_COLUMN_LUD("last_update_date"),

    SQL_GC_SELECT_ALL("SELECT * FROM gc.gift_certificate"),

    SQL_GC_SELECT_W_TAG_NAME("SELECT gift_certificate.* FROM gc.tag " +
            "left join  gc.gc_tag on tag.id = gc_tag.tag_id " +
            "left join gc.gift_certificate on gc_tag.gc_id = gift_certificate.id " +
            "where tag.name = ?"),

    ;

    private String string;

    GiftCertificateSQL(String string) {
        this.string = string;
    }

    public String getSQL() {
        return string;
    }

}
