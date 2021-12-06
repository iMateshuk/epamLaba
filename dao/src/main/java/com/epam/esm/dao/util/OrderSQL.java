package com.epam.esm.dao.util;

public enum OrderSQL {
  COUNT_ID("SELECT count(id) FROM Order o where id=:id"),
  ORDERS_USER_ID("SELECT o FROM User u JOIN u.orders o where u.id=:id"),
  ORDERS_COUNT_USER_ID("SELECT count(o) FROM User u JOIN u.orders o where u.id=:id"),
  ORDERS_ID_USER_ID("SELECT o FROM User u JOIN u.orders o where u.id=:uid and o.id=:oid"),

  ;

  private final String sql;

  OrderSQL(String string) {
    this.sql = string;
  }

  public String getSQL() {
    return sql;
  }
}
