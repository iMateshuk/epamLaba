package com.epam.esm.exception;

import lombok.Data;

import java.util.List;

@Data
public class GlobalExceptionDTO {
  private List<String> errorMessage;
  private int errorCode;

  public GlobalExceptionDTO(String errorMessage, int errorCode) {
    this.errorMessage = List.of(errorMessage);
    this.errorCode = errorCode;
  }

  public GlobalExceptionDTO(List<String> errorMessage, int errorCode) {
    this.errorMessage = errorMessage;
    this.errorCode = errorCode;
  }
}
