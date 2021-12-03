package com.epam.esm.hateoas;

import com.epam.esm.service.util.PageService;
import com.epam.esm.util.ControllerConvertor;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class PageControllerAssembler implements RepresentationModelAssembler<PageService<?>, PageControllerModel<?>> {
  private final ControllerConvertor convertor;

  @Override
  public PageControllerModel<?> toModel(PageService<?> ignore) {
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
