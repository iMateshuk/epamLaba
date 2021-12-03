package com.epam.esm.dao.util;

import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class PageEntity {
  @NonNull
  private Integer size;
  private Long totalElements;
  private Long totalPages;
  @NonNull
  private Integer number;
}
