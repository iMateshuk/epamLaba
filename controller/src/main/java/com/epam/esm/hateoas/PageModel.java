package com.epam.esm.hateoas;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class PageModel<T> extends RepresentationModel<PageModel<T>> {
  public List<T> list;
  public PageParamModel page;
}
