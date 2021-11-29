package com.epam.esm.service.exception;

import com.epam.esm.service.dto.ErrorDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ServiceException extends RuntimeException {
  private final ErrorDto errorDto;
  private final int errorCode;
}
