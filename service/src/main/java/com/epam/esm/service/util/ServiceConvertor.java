package com.epam.esm.service.util;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class ServiceConvertor {
  private final ModelMapper modelMapper;

  public <S, T> T toTarget(S source, Class<T> targetClass) {
    return Objects.isNull(source) ? null : modelMapper.map(source, targetClass);
  }

  public <S, T> List<T> toTarget(List<S> source, Class<T> targetClass) {
    return source
        .stream()
        .map(element -> modelMapper.map(element, targetClass))
        .collect(Collectors.toList());
  }
}
