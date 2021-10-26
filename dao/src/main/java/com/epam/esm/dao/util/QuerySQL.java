package com.epam.esm.dao.util;

public enum QuerySQL {

    SQL_COLUM_LABEL_ID("id"),
    SQL_COLUM_LABEL_TITLE("title"),
    SQL_COLUM_LABEL_U_ID("u_id"),
    SQL_COLUM_LABEL_COUNT("count"),

    ;

    private String string;

    QuerySQL(String string) {
        this.string = string;
    }

    public String getSQL() {
        return string;
    }

}
