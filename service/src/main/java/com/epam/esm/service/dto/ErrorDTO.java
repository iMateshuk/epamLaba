package com.epam.esm.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class ErrorDTO {
  private final String errorMsgKey;
  private Object[] params;

  public ErrorDTO(String errorMsgKey) {
    this.errorMsgKey = errorMsgKey;
  }

  public ErrorDTO(String errorMsgKey, Object... params) {
    this.errorMsgKey = errorMsgKey;
    this.params = params;
  }
}
