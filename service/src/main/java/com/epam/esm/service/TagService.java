package com.epam.esm.service;

import com.epam.esm.service.dto.TagDTO;

import java.util.List;

public interface TagService {
  TagDTO insertTag(String name);

  List<TagDTO> findAllTags();

  TagDTO findTag(int id);

  void deleteTag(int id);
}
