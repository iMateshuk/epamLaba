package com.epam.esm.dao.page;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageData {
  private Integer size;
  private Integer number;
}
