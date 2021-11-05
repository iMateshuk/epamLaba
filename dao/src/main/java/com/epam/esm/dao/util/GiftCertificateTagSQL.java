package com.epam.esm.dao.util;

public enum GiftCertificateTagSQL {

    INSERT_W_ID_NAME("INSERT INTO gc.gc_tag (gc_id, tag_id) VALUES(?,(SELECT id FROM gc.tag WHERE name = ?))"),

    SELECT_MAIN_SEARCH("SELECT gc.gift_certificate.* FROM gc.gift_certificate " +
            "left join gc.gc_tag on gift_certificate.id = gc_tag.gc_id " +
            "left join gc.tag on gc_tag.tag_id = tag.id "),

    DEL_W_GC_ID("DELETE FROM gc.gc_tag WHERE gc_id = ?"),

    SEARCH_TAG_NAME("tag.name = ? "),

    SEARCH_NAME("gift_certificate.name LIKE CONCAT('%',?,'%') "),

    SEARCH_DESCRIPTION("gift_certificate.description LIKE CONCAT('%',?,'%') "),

    APPEND_GROUP("GROUP BY gift_certificate.id "),

    SORT_DATE("gift_certificate.last_update_date "),

    SORT_NAME("gift_certificate.name "),


    ;

    private String string;

    GiftCertificateTagSQL(String string) {
        this.string = string;
    }

    public String getSQL() {
        return string;
    }
}
