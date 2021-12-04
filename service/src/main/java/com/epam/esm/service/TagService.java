package com.epam.esm.service;

import com.epam.esm.service.dto.PageDTO;
import com.epam.esm.service.dto.PageParamDTO;
import com.epam.esm.service.dto.TagDTO;

public interface TagService {
  TagDTO insertByName(String name);

  PageDTO<TagDTO> findAll(PageParamDTO pageParamDTO);

  TagDTO findById(Integer id);

  void deleteById(Integer id);
}
