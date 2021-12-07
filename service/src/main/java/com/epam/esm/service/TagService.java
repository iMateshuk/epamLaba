package com.epam.esm.service;

import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.page.Page;
import com.epam.esm.service.page.PageParam;

public interface TagService {
  TagDTO insertByName(String name);

  Page<TagDTO> findAll(PageParam pageParam);

  TagDTO findById(Integer id);

  void deleteById(Integer id);
}
