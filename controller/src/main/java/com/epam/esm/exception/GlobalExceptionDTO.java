package com.epam.esm.exception;

import java.util.Objects;

public class GlobalExceptionDTO {

    private String errorMessage;
    private int errorCode;

    public GlobalExceptionDTO() {
    }

    public GlobalExceptionDTO(String errorMessage, int errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GlobalExceptionDTO that = (GlobalExceptionDTO) o;
        return errorCode == that.errorCode && Objects.equals(errorMessage, that.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorMessage, errorCode);
    }

    @Override
    public String toString() {
        return getClass().getName() +
                " errorMessage='" + errorMessage + '\'' +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
