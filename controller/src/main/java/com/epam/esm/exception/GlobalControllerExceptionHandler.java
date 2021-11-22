package com.epam.esm.exception;

import com.epam.esm.service.dto.ErrorDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ServiceValidationException;
import com.epam.esm.service.exception.ValidationException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {
  private static final int MULTIPLIER = 1000;

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

  @ExceptionHandler(value = {ServiceValidationException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GlobalExceptionDTO handle(ServiceValidationException exception) {
    ErrorDto errorDto = exception.getErrorDto();
    Object[] params = errorDto.getParams() == null ? null : errorDto.getParams();
    return new GlobalExceptionDTO(
        messageSource.getMessage(errorDto.getErrorMsgKey(), params, LocaleContextHolder.getLocale()),
        HttpStatus.BAD_REQUEST.value() * MULTIPLIER + exception.getErrorCod()
    );
  }

  @ExceptionHandler(value = {ServiceException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public GlobalExceptionDTO handle(ServiceException exception) {
    ErrorDto errorDto = exception.getErrorDto();
    Object[] params = errorDto.getParams() == null ? null : errorDto.getParams();
    return new GlobalExceptionDTO(
        messageSource.getMessage(errorDto.getErrorMsgKey(), params, LocaleContextHolder.getLocale()),
        HttpStatus.NOT_FOUND.value() * MULTIPLIER + exception.getErrorCod()
    );
  }

  @ExceptionHandler(value = {EmptyResultDataAccessException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public GlobalExceptionDTO handle(EmptyResultDataAccessException e) {
    return new GlobalExceptionDTO(
        messageSource.getMessage(e.getClass().getSimpleName(), null, LocaleContextHolder.getLocale()),
        HttpStatus.NOT_FOUND.value() * MULTIPLIER + 303
    );
  }

  @ExceptionHandler(value = {DuplicateKeyException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GlobalExceptionDTO handle(DuplicateKeyException e) {
    return new GlobalExceptionDTO(
        messageSource.getMessage(e.getClass().getSimpleName(), null, LocaleContextHolder.getLocale()),
        HttpStatus.BAD_REQUEST.value() * MULTIPLIER + 304
    );
  }

  @ExceptionHandler(value = {IncorrectResultSizeDataAccessException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GlobalExceptionDTO handle(IncorrectResultSizeDataAccessException e) {
    return new GlobalExceptionDTO(
        messageSource.getMessage(e.getClass().getSimpleName(), null, LocaleContextHolder.getLocale()),
        HttpStatus.BAD_REQUEST.value() * MULTIPLIER + 305
    );
  }


  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GlobalExceptionDTO handleException(MethodArgumentTypeMismatchException e) {
    return new GlobalExceptionDTO(
        messageSource.getMessage(e.getClass().getSimpleName(), null, LocaleContextHolder.getLocale()),
        HttpStatus.BAD_REQUEST.value()
    );
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  public GlobalExceptionDTO handleException(HttpRequestMethodNotSupportedException e) {
    return new GlobalExceptionDTO(
        messageSource.getMessage(e.getClass().getSimpleName(), null, LocaleContextHolder.getLocale()),
        HttpStatus.METHOD_NOT_ALLOWED.value()
    );
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public GlobalExceptionDTO handleException(Exception e) {
    return new GlobalExceptionDTO(
        messageSource.getMessage(e.getClass().getSimpleName(), null, LocaleContextHolder.getLocale()),
        HttpStatus.INTERNAL_SERVER_ERROR.value()
    );
  }
}
