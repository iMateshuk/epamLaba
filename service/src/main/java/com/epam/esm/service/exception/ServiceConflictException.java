package com.epam.esm.service.exception;

import com.epam.esm.service.dto.ErrorDto;

public class ServiceConflictException extends RuntimeException {
  private final ErrorDto errorDto;
  private final int errorCod;

  public ServiceConflictException(ErrorDto errorDto, int errorCod) {
    this.errorDto = errorDto;
    this.errorCod = errorCod;
  }

  public ErrorDto getErrorDto() {
    return errorDto;
  }

  public int getErrorCod() {
    return errorCod;
  }
}
