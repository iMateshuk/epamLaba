package com.epam.esm.exception;

import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidationException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {
  private static final int MULTIPLIER = 1000;

  private static final String DAO_EMPTY_RESULT = "dao.empty.result.error";
  private static final String DAO_DUP_KEY = "dao.duplicate.key.error";
  private static final String DAO_INCORRECT_RESULT = "dao.incorrect.result.error";

  private final MessageSource messageSource;

  public GlobalControllerExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(value = {ValidationException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GlobalExceptionDTO handle(ValidationException exception) {

    List<String> validationErrors = exception.getValidationErrors().stream()
        .map(error -> messageSource.getMessage(error.getErrorMsgKey(), error.getParams(), LocaleContextHolder.getLocale()))
        .collect(Collectors.toList());
    return new GlobalExceptionDTO(validationErrors,
        HttpStatus.BAD_REQUEST.value() * MULTIPLIER + exception.getErrorCod());
  }

  @ExceptionHandler(value = {ServiceException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GlobalExceptionDTO handle(ServiceException exception) {
    return new GlobalExceptionDTO(messageSource.getMessage(exception.getErrorDto().getErrorMsgKey(), null, LocaleContextHolder.getLocale()),
        HttpStatus.BAD_REQUEST.value() * MULTIPLIER + exception.getErrorCod());
  }

  @ExceptionHandler(value = {EmptyResultDataAccessException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public GlobalExceptionDTO handle(EmptyResultDataAccessException exception) {
    return new GlobalExceptionDTO(messageSource.getMessage(DAO_EMPTY_RESULT, null, LocaleContextHolder.getLocale()),
        HttpStatus.NOT_FOUND.value() * MULTIPLIER + 303);
  }

  @ExceptionHandler(value = {DuplicateKeyException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GlobalExceptionDTO handle(DuplicateKeyException exception) {
    return new GlobalExceptionDTO(messageSource.getMessage(DAO_DUP_KEY, null, LocaleContextHolder.getLocale()),
        HttpStatus.BAD_REQUEST.value() * MULTIPLIER + 304);
  }

  @ExceptionHandler(value = {IncorrectResultSizeDataAccessException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GlobalExceptionDTO handle(IncorrectResultSizeDataAccessException exception) {
    return new GlobalExceptionDTO(messageSource.getMessage(DAO_INCORRECT_RESULT, null, LocaleContextHolder.getLocale()),
        HttpStatus.BAD_REQUEST.value() * MULTIPLIER + 305);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public GlobalExceptionDTO handleException(Exception e) {
    return new GlobalExceptionDTO(e.getClass().getCanonicalName(), HttpStatus.INTERNAL_SERVER_ERROR.value());
  }
}
