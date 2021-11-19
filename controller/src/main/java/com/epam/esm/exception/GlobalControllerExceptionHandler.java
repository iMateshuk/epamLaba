package com.epam.esm.exception;

import com.epam.esm.service.dto.ErrorDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidationException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final int MULTIPLIER = 100;

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

    @ExceptionHandler(value = {ServiceException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GlobalExceptionDTO handle(ServiceException exception) {

        return new GlobalExceptionDTO(exception.getErrorDto().getErrorMsgKey(),
                HttpStatus.BAD_REQUEST.value() * MULTIPLIER + exception.getErrorCod());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleException(Exception e) {

        return new ErrorDto(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
