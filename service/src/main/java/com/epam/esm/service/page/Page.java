package com.epam.esm.service.page;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Page<T> {
  private Integer pageSize;
  private Integer pageNumber;
  private Long totalElements;
  private Long totalPages;
  private List<T> list;
}
