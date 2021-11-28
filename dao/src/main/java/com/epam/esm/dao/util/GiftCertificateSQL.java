package com.epam.esm.dao.util;

public enum GiftCertificateSQL {
  QL_SELECT_ALL("SELECT certificate FROM Certificate certificate"),
  QL_SELECT_ALL_W_NAME("SELECT certificate FROM Certificate certificate WHERE certificate.name = :name"),

  ;

  private final String sql;

  GiftCertificateSQL(String string) {
    this.sql = string;
  }

  public String getSQL() {
    return sql;
  }
}
