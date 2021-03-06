package com.epam.esm.hateoas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class PageModel<T> extends RepresentationModel<PageModel<T>> {
  private Integer pageSize;
  private Integer pageNumber;
  private Long totalElements;
  private Long lastPage;
  private List<T> list;
}
