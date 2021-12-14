package com.epam.esm.dao.util;

public enum PredicateParameter {
  JOIN_TAG_NAME("name"),

  SEARCH_CERT_NAME("name"),
  SEARCH_CERT_DESC("description"),

  SORT_CERT_NAME("name"),
  SORT_CERT_DATE("lastUpdateDate"),

  ORDER_DESC("desc"),
  ORDER_ASC("asc"),

  ;

  private final String sql;

  PredicateParameter(String string) {
    this.sql = string;
  }

  public String getSQL() {
    return sql;
  }
}
