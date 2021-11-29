package com.epam.esm.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
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
}
