package com.epam.esm.service.page;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageParam {
  private Integer size;
  private Integer number;
}
