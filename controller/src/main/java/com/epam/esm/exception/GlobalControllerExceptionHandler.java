package com.epam.esm.exception;

import com.epam.esm.service.dto.ErrorDTO;
import com.epam.esm.service.exception.*;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@AllArgsConstructor
public class GlobalControllerExceptionHandler {
  private final ExceptionUtil exceptionUtil;

  @ExceptionHandler(value = {ValidationException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GlobalExceptionDTO handle(ValidationException exception) {
    return exceptionUtil.createDto(exception.getErrorCode(), HttpStatus.BAD_REQUEST,
        exception.getValidationErrors().toArray(new ErrorDTO[0]));
  }

  @ExceptionHandler(value = {ServiceValidationException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GlobalExceptionDTO handle(ServiceValidationException exception) {
    return exceptionUtil.createDto(exception.getErrorCode(), HttpStatus.BAD_REQUEST, exception.getErrorDto());
  }

  @ExceptionHandler(value = {ServiceException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public GlobalExceptionDTO handle(ServiceException exception) {
    return exceptionUtil.createDto(exception.getErrorCode(), HttpStatus.NOT_FOUND, exception.getErrorDto());
  }

  @ExceptionHandler(value = {ServiceConflictException.class})
  @ResponseStatus(HttpStatus.CONFLICT)
  public GlobalExceptionDTO handle(ServiceConflictException exception) {
    return exceptionUtil.createDto(exception.getErrorCode(), HttpStatus.CONFLICT, exception.getErrorDto());
  }

  @ExceptionHandler(value = {ServiceListException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public GlobalExceptionDTO handle(ServiceListException exception) {
    return exceptionUtil.createDto(exception.getErrorCode(), HttpStatus.NOT_FOUND,
        exception.getErrorDto().toArray(new ErrorDTO[0]));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GlobalExceptionDTO handle(MethodArgumentNotValidException exception) {
    return exceptionUtil.createDto(exception.getErrorCount(), HttpStatus.BAD_REQUEST,
        exception.getBindingResult().getAllErrors());
  }

  @ExceptionHandler(value = {EmptyResultDataAccessException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public GlobalExceptionDTO handle(EmptyResultDataAccessException e) {
    return exceptionUtil.createDto(HttpStatus.NOT_FOUND.value(), e.getClass().getSimpleName());
  }

  @ExceptionHandler(value = {DuplicateKeyException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GlobalExceptionDTO handle(DuplicateKeyException e) {
    return exceptionUtil.createDto(HttpStatus.BAD_REQUEST.value(), e.getClass().getSimpleName());
  }

  @ExceptionHandler(value = {IncorrectResultSizeDataAccessException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GlobalExceptionDTO handle(IncorrectResultSizeDataAccessException e) {
    return exceptionUtil.createDto(HttpStatus.BAD_REQUEST.value(), e.getClass().getSimpleName());
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GlobalExceptionDTO handleException(MethodArgumentTypeMismatchException e) {
    return exceptionUtil.createDto(HttpStatus.BAD_REQUEST.value(), e.getClass().getSimpleName());
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  public GlobalExceptionDTO handleException(HttpRequestMethodNotSupportedException e) {
    return exceptionUtil.createDto(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getClass().getSimpleName());
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  public GlobalExceptionDTO handleException(HttpMediaTypeNotSupportedException e) {
    return exceptionUtil.createDto(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getClass().getSimpleName());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
  public GlobalExceptionDTO handleException(Exception e) {
    return exceptionUtil.createDto(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getClass().getSimpleName());
  }
}
