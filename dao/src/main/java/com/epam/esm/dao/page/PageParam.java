package com.epam.esm.dao.page;

import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class PageParam {
  @NonNull
  private Integer size;
  private Long totalElements;
  private Long totalPages;
  @NonNull
  private Integer number;
}
