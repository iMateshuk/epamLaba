package com.epam.esm.dao.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PageDAO<T> {
  public List<T> list;
  public PageEntity page;
}
