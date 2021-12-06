package com.epam.esm.dao.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Page<T> {
  public List<T> list;
  public PageParam page;
}
