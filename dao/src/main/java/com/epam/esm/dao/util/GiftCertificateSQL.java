package com.epam.esm.dao.util;

public enum GiftCertificateSQL {
  SELECT_ALL_BY_NAME("SELECT certificate FROM Certificate certificate WHERE certificate.name = :name"),

  ;

  private final String sql;

  GiftCertificateSQL(String string) {
    this.sql = string;
  }

  public String getSQL() {
    return sql;
  }
}
