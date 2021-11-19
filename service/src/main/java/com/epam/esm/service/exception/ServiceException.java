package com.epam.esm.service.exception;

import com.epam.esm.service.dto.ErrorDto;

public class ServiceException extends RuntimeException {
    private final ErrorDto errorDto;
    private final int errorCod;

    public ServiceException(ErrorDto errorDto, int errorCod) {
        this.errorDto = errorDto;
        this.errorCod = errorCod;
    }

    public ErrorDto getErrorDto() {
        return errorDto;
    }

    public int getErrorCod() {
        return errorCod;
    }
}
