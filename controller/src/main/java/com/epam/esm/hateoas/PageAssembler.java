package com.epam.esm.hateoas;

import com.epam.esm.service.page.Page;
import com.epam.esm.service.util.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class PageAssembler implements RepresentationModelAssembler<Page<?>, PageModel<?>> {
  private final Mapper mapper;

  @Override
  public PageModel<?> toModel(Page<?> ignore) {
    return null;
  }

  public <T, S> T toModel(S source, Class<T> targetClass) {
    return mapper.toTarget(source, targetClass);
  }

  public <S, T> List<T> toModel(List<S> source, Class<T> targetClass) {
    return source
        .stream()
        .map(element -> mapper.toTarget(element, targetClass))
        .collect(Collectors.toList());
  }
}
