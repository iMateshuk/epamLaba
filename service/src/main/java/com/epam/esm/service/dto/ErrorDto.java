package com.epam.esm.service.dto;

import java.util.Arrays;
import java.util.Objects;

public class ErrorDto {
  private final String errorMsgKey;
  private Object[] params;

  public ErrorDto(String errorMsgKey) {
    this.errorMsgKey = errorMsgKey;
  }

  public ErrorDto(String errorMsgKey, Object... params) {
    this.errorMsgKey = errorMsgKey;
    this.params = params;
  }

  public String getErrorMsgKey() {
    return errorMsgKey;
  }

  public Object[] getParams() {
    return params;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorDto errorDto = (ErrorDto) o;
    return Objects.equals(errorMsgKey, errorDto.errorMsgKey) && Arrays.equals(params, errorDto.params);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(errorMsgKey);
    result = 31 * result + Arrays.hashCode(params);
    return result;
  }

  @Override
  public String toString() {
    return getClass().getName() +
        "errorMsgKey='" + errorMsgKey + '\'' +
        ", params=" + Arrays.toString(params) +
        '}';
  }
}
