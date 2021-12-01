package com.epam.esm.service.exception;

import com.epam.esm.service.dto.ErrorDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ServiceListException extends RuntimeException {
  private final List<ErrorDto> errorDto;
  private final int errorCode;
}
