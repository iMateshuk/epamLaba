package com.epam.esm.service.dto;

import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO {
  @NonNull
  private Integer size;
  private Long totalElements;
  private Long totalPages;
  @NonNull
  private Integer number;
}
