package com.epam.esm.exception;

import java.util.List;

public class GlobalExceptionDTO {
    private List<String> errorMessage;
    private int errorCode;

    public GlobalExceptionDTO() {
    }

    public GlobalExceptionDTO(String errorMessage, int errorCode) {
        this.errorMessage = List.of(errorMessage);
        this.errorCode = errorCode;
    }

    public GlobalExceptionDTO(List<String> errorMessage, int errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public List<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(List<String> errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
