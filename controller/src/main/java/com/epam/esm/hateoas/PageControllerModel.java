package com.epam.esm.hateoas;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class PageControllerModel<T> extends RepresentationModel<PageControllerModel<T>> {
  public List<T> list;
  public PageModel page;
}
