package com.epam.esm.dao.util;

public enum GiftCertificateSQL {
  SELECT_ALL("SELECT certificate FROM Certificate certificate"),
  COUNT_ALL("SELECT count(id) FROM Certificate certificate"),
  COUNT_ID("SELECT count(id) FROM Certificate certificate WHERE id=:id"),
  SELECT_ALL_BY_NAME("SELECT certificate FROM Certificate certificate WHERE certificate.name = :name"),

  SELECT_MAIN_SEARCH("SELECT c.* FROM gc.gift_certificate c " +
      "left join gc.gc_tag gt on c.id = gt.gc_id " +
      "left join gc.tags t on gt.tag_id = t.id "),
  JOIN_TAG_NAME("t.name in (?) "),
  SEARCH_CERT_NAME("c.name LIKE CONCAT('%',?,'%') "),
  SEARCH_CERT_DESC("c.description LIKE CONCAT('%',?,'%') "),
  APPEND_GROUP("GROUP BY c.id "),

  ;

  private final String sql;

  GiftCertificateSQL(String string) {
    this.sql = string;
  }

  public String getSQL() {
    return sql;
  }
}
