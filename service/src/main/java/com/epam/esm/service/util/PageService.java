package com.epam.esm.service.util;

import com.epam.esm.service.dto.PageDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class PageService<T> {
  public List<T> list;
  public PageDTO page;
  Integer count;
}
