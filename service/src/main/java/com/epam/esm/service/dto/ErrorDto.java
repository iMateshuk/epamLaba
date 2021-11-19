package com.epam.esm.service.dto;

public class ErrorDto {
    private final String errorMsgKey;
    private Object[] params;

    public ErrorDto(String errorMsgKey) {
        this.errorMsgKey = errorMsgKey;
    }

    public ErrorDto(String errorMsgKey, Object... params) {
        this.errorMsgKey = errorMsgKey;
        this.params = params;
    }

    public String getErrorMsgKey() {
        return errorMsgKey;
    }

    public Object[] getParams() {
        return params;
    }

}
