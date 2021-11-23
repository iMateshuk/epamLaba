package com.epam.esm.service.exception;

import com.epam.esm.service.dto.ErrorDto;

public class ServiceValidationException extends RuntimeException {
  private final ErrorDto errorDto;
  private final int errorCode;

  public ServiceValidationException(ErrorDto errorDto, int errorCod) {
    this.errorDto = errorDto;
    this.errorCode = errorCod;
  }

  public ErrorDto getErrorDto() {
    return errorDto;
  }

  public int getErrorCode() {
    return errorCode;
  }
}
