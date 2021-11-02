package com.epam.esm.dao.util;

public enum GiftCertificateTagSQL {

    INSERT_W_ID_NAME("INSERT INTO gc.gc_tag (gc_id, tag_id) VALUES(?,(SELECT id FROM gc.tag WHERE name = ?))"),

    SELECT_MAIN_SEARCH("SELECT gc.gift_certificate.* FROM gc.gift_certificate " +
            "left join gc.gc_tag on gift_certificate.id = gc_tag.gc_id " +
            "left join gc.tag on gc_tag.tag_id = tag.id "),

    DEL_W_GC_ID("DELETE FROM gc.gc_tag WHERE gc_id = ?"),

    APPEND_W_TAG_NAME("WHERE tag.name = ? "),
    APPEND_AND_TAG_NAME("AND tag.name = ? "),

    APPEND_W_SEARCH_NAME("WHERE gift_certificate.name LIKE CONCAT('%',?,'%') "),
    APPEND_AND_SEARCH_NAME("OR gift_certificate.name LIKE CONCAT('%',?,'%') "),

    APPEND_W_SEARCH_DESCRIPTION("WHERE gift_certificate.description LIKE CONCAT('%',?,'%') "),
    APPEND_AND_SEARCH_DESCRIPTION("OR gift_certificate.description LIKE CONCAT('%',?,'%') "),

    APPEND_GROUP("GROUP BY gift_certificate.id "),

    APPEND_ORDER_DATE("ORDER BY gift_certificate.last_update_date "),

    APPEND_ORDER_NAME("ORDER BY gift_certificate.name "),


    ;

    private String string;

    GiftCertificateTagSQL(String string) {
        this.string = string;
    }

    public String getSQL() {
        return string;
    }
}
