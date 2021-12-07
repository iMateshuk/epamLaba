package com.epam.esm.service.page;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Page<T> {
  @NonNull
  private Integer size;
  @NonNull
  private Integer number;
  private Long totalElements;
  private Long totalPages;
  private List<T> list;
}
