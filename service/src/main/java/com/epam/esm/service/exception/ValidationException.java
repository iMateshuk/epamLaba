package com.epam.esm.service.exception;

import com.epam.esm.service.dto.ErrorDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ValidationException extends RuntimeException {
  private final List<ErrorDTO> validationErrors;
  private final int errorCode;
}

