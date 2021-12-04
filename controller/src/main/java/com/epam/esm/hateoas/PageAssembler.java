package com.epam.esm.hateoas;

import com.epam.esm.service.dto.PageDTO;
import com.epam.esm.service.util.ServiceConvertor;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class PageAssembler implements RepresentationModelAssembler<PageDTO<?>, PageModel<?>> {
  private final ServiceConvertor convertor;

  @Override
  public PageModel<?> toModel(PageDTO<?> ignore) {
    return null;
  }

  public <T, S> T toModel(S source, Class<T> targetClass) {
    return convertor.toTarget(source, targetClass);
  }

  public <S, T> List<T> toModel(List<S> source, Class<T> targetClass) {
    return source
        .stream()
        .map(element -> convertor.toTarget(element, targetClass))
        .collect(Collectors.toList());
  }
}
