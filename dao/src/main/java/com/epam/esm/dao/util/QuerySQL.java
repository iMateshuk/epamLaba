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

    ;

    private String string;

    QuerySQL(String string) {
        this.string = string;
    }

    public String getSQL() {
        return string;
    }

}
