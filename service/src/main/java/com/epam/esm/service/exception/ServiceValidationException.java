package com.epam.esm.service.exception;

import com.epam.esm.service.dto.ErrorDto;

public class ServiceValidationException extends RuntimeException {
  private final ErrorDto errorDto;
  private final int errorCod;

  public ServiceValidationException(ErrorDto errorDto, int errorCod) {
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
