package com.epam.esm.service.exception;

import com.epam.esm.service.dto.ErrorDto;

public class ServiceConflictException extends RuntimeException {
  private final ErrorDto errorDto;
  private final int errorCode;

  public ServiceConflictException(ErrorDto errorDto, int errorCod) {
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
