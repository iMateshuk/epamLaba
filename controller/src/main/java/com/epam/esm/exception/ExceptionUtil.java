package com.epam.esm.exception;

import com.epam.esm.service.dto.ErrorDTO;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class ExceptionUtil {
  private static final int MULTIPLIER = 1000;
  private final MessageSource messageSource;

  public GlobalExceptionDTO createDto(Integer errorCode, HttpStatus status, ErrorDTO... errorDto) {
    List<String> validationErrors = Arrays.stream(errorDto)
        .map(error -> messageSource.getMessage(error.getErrorMsgKey(), error.getParams(), LocaleContextHolder.getLocale()))
        .collect(Collectors.toList());
    return new GlobalExceptionDTO(validationErrors, status.value() * MULTIPLIER + errorCode);
  }

  public GlobalExceptionDTO createDto(Integer errorCode, String simpleName) {
    return new GlobalExceptionDTO(simpleName, errorCode);
  }
}
