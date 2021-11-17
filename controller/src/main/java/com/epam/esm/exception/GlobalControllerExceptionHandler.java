package com.epam.esm.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GlobalExceptionDTO handle(IllegalArgumentException e) {

        return createDTO(404001, e);
    }

    @ExceptionHandler(value = {EmptyResultDataAccessException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GlobalExceptionDTO handle(EmptyResultDataAccessException e) {

        return createDTO(404002, e);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GlobalExceptionDTO handle(NoSuchElementException e) {

        return createDTO(404003, e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GlobalExceptionDTO handleException(Exception e) {

        return createDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
    }

    private GlobalExceptionDTO createDTO(int errorCode, Throwable e) {

        GlobalExceptionDTO globalExceptionDTO = new GlobalExceptionDTO();

        globalExceptionDTO.setErrorCode(errorCode);
        globalExceptionDTO.setErrorMessage(e.getLocalizedMessage());

        return globalExceptionDTO;
    }
}
