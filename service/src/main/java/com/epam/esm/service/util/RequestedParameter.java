package com.epam.esm.service.util;

public enum RequestedParameter {
  JOIN_TAG_NAME("tagName"),

  SEARCH_CERT_NAME("certName"),
  SEARCH_CERT_DESC("certDesc"),

  SORT_CERT_NAME("sortName"),
  SORT_CERT_DATE("sortDate"),

  ;

  private final String parameter;

  RequestedParameter(String string) {
    this.parameter = string;
  }

  public String getParameterKey() {
    return parameter;
  }

}
