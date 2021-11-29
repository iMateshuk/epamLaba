package com.epam.esm.dao.util;

public enum UserSQL {
  SELECT_ALL("SELECT u FROM User u"),
  SELECT_ORDERS("SELECT c FROM Order o JOIN User u where u.id=:id"),
  SELECT_ORDER("SELECT u FROM User u"),

  ;

  private final String sql;

  UserSQL(String string) {
    this.sql = string;
  }

  public String getSQL() {
    return sql;
  }
}
