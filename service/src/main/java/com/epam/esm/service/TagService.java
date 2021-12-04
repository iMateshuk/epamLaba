package com.epam.esm.service;

import com.epam.esm.service.dto.PageDTO;
import com.epam.esm.service.dto.PageParamDTO;
import com.epam.esm.service.dto.TagDTO;

public interface TagService {
  TagDTO insertByName(String name);

  PageDTO<TagDTO> findAll(PageParamDTO pageParamDTO);

  PageDTO<TagDTO> findById(Integer id, PageParamDTO pageParamDTO);

  void deleteById(Integer id);
}
