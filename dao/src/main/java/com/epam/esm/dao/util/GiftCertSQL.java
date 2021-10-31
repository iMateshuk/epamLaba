package com.epam.esm.dao.util;

public enum GiftCertSQL {

    SELECT_ALL("SELECT * FROM gc.gift_certificate"),
    SELECT_ALL_W_ID("SELECT * FROM gc.gift_certificate WHERE id = ?"),

    DEL_DB_CASCADE_W_ID("DELETE FROM gc.gift_certificate WHERE id = ?"),

    INSERT_GIFT_CERT("INSERT INTO gc.gift_certificate(name,description,price,duration,create_date,last_update_date) VALUES(?,?,?,?,?,?)"),

    UPDATE_DATA_IF_NOT_NULL_EMPTY("UPDATE gc.gift_certificate SET name=COALESCE(NULLIF(?, ''), name), " +
            "description=COALESCE(NULLIF(?, ''), description), price=COALESCE(NULLIF(?, ''), price), " +
            "duration=COALESCE(NULLIF(?, ''), duration), last_update_date=COALESCE(NULLIF(?, ''), last_update_date) WHERE id = ?"),

    ;

    private String string;

    GiftCertSQL(String string) {
        this.string = string;
    }

    public String getSQL() {
        return string;
    }

}
