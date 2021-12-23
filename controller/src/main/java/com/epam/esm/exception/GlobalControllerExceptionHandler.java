package com.epam.esm.exception;

import com.epam.esm.service.dto.ErrorDTO;
import com.epam.esm.service.exception.ServiceAccessException;
import com.epam.esm.service.exception.ServiceConflictException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ServiceValidationException;
import com.epam.esm.service.exception.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.NoResultException;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@AllArgsConstructor
public class GlobalControllerExceptionHandler {
  private final ExceptionUtil exceptionUtil;

  @ExceptionHandler(value = {ValidationException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GlobalExceptionDTO handle(ValidationException ex) {
    return exceptionUtil.createDto(ex.getErrorCode(), HttpStatus.BAD_REQUEST,
        ex.getValidationErrors().toArray(ErrorDTO[]::new));
  }

  @ExceptionHandler(value = {ServiceValidationException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GlobalExceptionDTO handle(ServiceValidationException ex) {
    return exceptionUtil.createDto(ex.getErrorCode(), HttpStatus.BAD_REQUEST, ex.getErrorDto());
  }

  @ExceptionHandler(value = {ServiceException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public GlobalExceptionDTO handle(ServiceException ex) {
    return exceptionUtil.createDto(ex.getErrorCode(), HttpStatus.NOT_FOUND, ex.getErrorDto());
  }

  @ExceptionHandler(value = {ServiceConflictException.class})
  @ResponseStatus(HttpStatus.CONFLICT)
  public GlobalExceptionDTO handle(ServiceConflictException ex) {
    return exceptionUtil.createDto(ex.getErrorCode(), HttpStatus.CONFLICT, ex.getErrorDto());
  }

  @ExceptionHandler(value = {ServiceAccessException.class})
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public GlobalExceptionDTO handle(ServiceAccessException ex) {
    return exceptionUtil.createDto(ex.getErrorCode(), HttpStatus.FORBIDDEN, ex.getErrorDto());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GlobalExceptionDTO handle(MethodArgumentNotValidException ex) {
    return exceptionUtil.createDto(ex.getErrorCount(), HttpStatus.BAD_REQUEST,
        ex.getBindingResult().getAllErrors());
  }

  @ExceptionHandler(value = {EmptyResultDataAccessException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public GlobalExceptionDTO handle(EmptyResultDataAccessException e) {
    return exceptionUtil.createDto(HttpStatus.NOT_FOUND.value(), e.getClass().getSimpleName());
  }

  @ExceptionHandler(NoResultException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public GlobalExceptionDTO handleException(NoResultException e) {
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
  public GlobalExceptionDTO handleException(MethodArgumentTypeMismatchException ex) {

    return exceptionUtil.createDto(
        HttpStatus.BAD_REQUEST.value(),
        HttpStatus.BAD_REQUEST,
        new ErrorDTO(ex.getClass().getSimpleName(), ex.getParameter().getParameterName(), ex.getValue()));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GlobalExceptionDTO handleException(ConstraintViolationException ex) {
    return exceptionUtil.createDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, ex);
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

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public GlobalExceptionDTO handleException(AccessDeniedException e) {
    return exceptionUtil.createDto(HttpStatus.FORBIDDEN.value(), e.getClass().getSimpleName());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
  public GlobalExceptionDTO handleException(Exception e) {
    return exceptionUtil.createDto(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getClass().getSimpleName());
  }
}
