package com.epam.esm.service.exception;

import com.epam.esm.service.dto.ErrorDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ServiceConflictException extends RuntimeException {
  private final ErrorDTO errorDto;
  private final int errorCode;
}
