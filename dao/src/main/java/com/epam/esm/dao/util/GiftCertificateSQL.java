package com.epam.esm.dao.util;

public enum GiftCertificateSQL {
  SELECT_ALL("SELECT certificate FROM Certificate certificate"),
  COUNT_ALL("SELECT count(id) FROM Certificate certificate"),
  COUNT_ID("SELECT count(id) FROM Certificate certificate WHERE id=:id"),
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
