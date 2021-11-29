package com.epam.esm.exception;

import com.epam.esm.service.dto.ErrorDto;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ExceptionUtil {
  private static final int MULTIPLIER = 1000;
  private final MessageSource messageSource;

  public GlobalExceptionDTO createDto(Integer errorCode, HttpStatus status, ErrorDto... errorDto) {
    List<String> validationErrors = Arrays.stream(errorDto)
        .map(error -> {
          Object[] params = error.getParams() == null ? null : error.getParams();
          return messageSource.getMessage(error.getErrorMsgKey(), params, LocaleContextHolder.getLocale());
        })
        .collect(Collectors.toList());
    return new GlobalExceptionDTO(validationErrors, status.value() * MULTIPLIER + errorCode);
  }

  public GlobalExceptionDTO createDto(Integer errorCode, String simpleName) {
    return new GlobalExceptionDTO(simpleName, errorCode);
  }
}
