package com.epam.esm.dao.util;

public enum UserSQL {
  QL_SELECT_ALL("SELECT u FROM User u"),

  ;

  private final String sql;

  UserSQL(String string) {
    this.sql = string;
  }

  public String getSQL() {
    return sql;
  }
}
