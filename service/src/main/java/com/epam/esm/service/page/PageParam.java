package com.epam.esm.service.page;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageParam {
  private Integer pageSize;
  private Integer pageNumber;
}
