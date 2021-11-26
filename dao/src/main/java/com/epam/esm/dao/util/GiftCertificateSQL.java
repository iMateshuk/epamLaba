package com.epam.esm.dao.util;

public enum GiftCertificateSQL {
  SELECT_ALL("SELECT * FROM gc.gift_certificate"),
  SELECT_W_ID("SELECT * FROM gc.gift_certificate WHERE id = ?"),
  SELECT_W_NAME("SELECT * FROM gc.gift_certificate WHERE name = ?"),
  SELECT_LAST_INSERT_ID("SELECT last_insert_id()"),

  SELECT_COUNT_W_ID("SELECT count(*) FROM gc.gift_certificate WHERE id = ?"),
  SELECT_COUNT_W_NAME("SELECT count(*) FROM gc.gift_certificate WHERE name = ?"),

  SELECT_W_TAG_NAME("SELECT gift_certificate.* FROM gc.tag " +
      "left join  gc.gc_tag on tag.id = gc_tag.tag_id " +
      "left join gc.gift_certificate on gc_tag.gc_id = gift_certificate.id " +
      "where tag.name = ?"),

  DEL_DB_CASCADE_W_ID("DELETE FROM gc.gift_certificate WHERE id = ?"),

  INSERT_GIFT_CERT("INSERT INTO gc.gift_certificate(name,description,price,duration,create_date,last_update_date) " +
      "VALUES(?,?,?,?,UTC_TIMESTAMP,UTC_TIMESTAMP)"),

  INSERT_GIFT_CERT_TST("INSERT INTO gc.gift_certificate(name,description,price,duration,create_date,last_update_date) " +
      "VALUES(?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)"),

  UPDATE_DATA_IF_NOT_NULL_EMPTY("UPDATE gc.gift_certificate SET name=COALESCE(NULLIF(?, ''), name), " +
      "description=COALESCE(NULLIF(?, ''), description), price=COALESCE(NULLIF(?, ''), price), " +
      "duration=COALESCE(NULLIF(?, ''), duration), last_update_date=UTC_TIMESTAMP WHERE id = ?"),

  UPDATE_DATA_IF_NOT_NULL_EMPTY_TST("UPDATE gc.gift_certificate SET name=?, " +
      "description=?, price=?, duration=?, last_update_date=CURRENT_TIMESTAMP WHERE id = ?"),


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
