package com.epam.esm.hateoas;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageParamModel extends RepresentationModel<PageParamModel> {
  private Integer size;
  private Long totalElements;
  private Long totalPages;
  private Integer number;
}
