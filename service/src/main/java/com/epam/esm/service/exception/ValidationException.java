package com.epam.esm.service.exception;

import com.epam.esm.service.dto.ErrorDto;

import java.util.List;

public class ValidationException extends RuntimeException{
    private final List<ErrorDto> validationErrors;
    private final int errorCod;

    public ValidationException(List<ErrorDto> validationErrors, int errorCod) {
        this.validationErrors = validationErrors;
        this.errorCod = errorCod;
    }

    public List<ErrorDto> getValidationErrors() {
       return validationErrors;
    }

    public int getErrorCod() {
        return errorCod;
    }
}

