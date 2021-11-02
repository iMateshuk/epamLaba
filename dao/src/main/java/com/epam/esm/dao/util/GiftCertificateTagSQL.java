package com.epam.esm.dao.util;

public enum GiftCertificateTagSQL {

    INSERT_W_ID_NAME("INSERT INTO gc.gc_tag (gc_id, tag_id) VALUES(?,(SELECT id FROM gc.tag WHERE name = ?))"),

    DEL_W_GC_ID("DELETE FROM gc.gc_tag WHERE gc_id = ?"),

    ;

    private String string;

    GiftCertificateTagSQL(String string) {
        this.string = string;
    }

    public String getSQL() {
        return string;
    }
}
