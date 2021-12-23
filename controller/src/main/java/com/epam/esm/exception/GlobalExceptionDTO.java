package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GlobalExceptionDTO {
  private List<String> errorMessage;
  private int errorCode;

  public GlobalExceptionDTO(String errorMessage, int errorCode) {
    this.errorMessage = List.of(errorMessage);
    this.errorCode = errorCode;
  }
}
