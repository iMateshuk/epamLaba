package com.epam.esm.dao.util;

public enum GiftCertificateSQL {

    SELECT_ALL("SELECT * FROM gc.gift_certificate"),
    SELECT_ALL_W_ID("SELECT * FROM gc.gift_certificate WHERE id = ?"),
    SELECT_LAST_INSERT_ID("SELECT last_insert_id()"),

    SELECT_W_TAG_NAME("SELECT gift_certificate.* FROM gc.tag " +
            "left join  gc.gc_tag on tag.id = gc_tag.tag_id " +
            "left join gc.gift_certificate on gc_tag.gc_id = gift_certificate.id " +
            "where tag.name = ?"),

    DEL_DB_CASCADE_W_ID("DELETE FROM gc.gift_certificate WHERE id = ?"),

    INSERT_GIFT_CERT("INSERT INTO gc.gift_certificate(name,description,price,duration,create_date,last_update_date) VALUES(?,?,?,?,NOW(),NOW())"),

    UPDATE_DATA_IF_NOT_NULL_EMPTY("UPDATE gc.gift_certificate SET name=COALESCE(NULLIF(?, ''), name), " +
            "description=COALESCE(NULLIF(?, ''), description), price=COALESCE(NULLIF(?, ''), price), " +
            "duration=COALESCE(NULLIF(?, ''), duration), last_update_date=COALESCE(NULLIF(?, ''), last_update_date) WHERE id = ?"),

    ;

    private String string;

    GiftCertificateSQL(String string) {
        this.string = string;
    }

    public String getSQL() {
        return string;
    }

}
