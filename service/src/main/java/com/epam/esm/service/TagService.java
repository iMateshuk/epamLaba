package com.epam.esm.service;

import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.page.Page;
import com.epam.esm.service.page.PageParam;

public interface TagService {
  /**
   *
   * @param name
   * @return new tagDTO
   */
  TagDTO insertByName(String name);

  /**
   *
   * @param pageParam
   * @return page, include list of tagDTOs
   */
  Page<TagDTO> findAll(PageParam pageParam);

  /**
   *
   * @param id
   * @return tagDTO found by id
   */
  TagDTO findById(Integer id);

  /**
   *
   * @param id
   */
  void deleteById(Integer id);
}
