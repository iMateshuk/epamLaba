package com.epam.esm.dao.util;

public enum GiftCertificateTagSQL {

    INSERT_W_ID_NAME("INSERT INTO gc.gc_tag (gc_id, tag_id) VALUES(?,(SELECT id FROM gc.tag WHERE name = ?))"),

    ;

    private String string;

    GiftCertificateTagSQL(String string) {
        this.string = string;
    }

    public String getSQL() {
        return string;
    }
}
