package com.epam.esm.service.exception;

import com.epam.esm.service.dto.ErrorDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@AllArgsConstructor
@Getter
public class ValidationException extends RuntimeException{
    private final List<ErrorDto> validationErrors;
    private final int errorCod;
}

