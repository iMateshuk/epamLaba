package com.epam.esm.service;

import com.epam.esm.service.dto.TagDTO;

import java.util.List;

public interface TagService {
  TagDTO insertByName(String name);

  List<TagDTO> findAll();

  TagDTO findById(int id);

  void deleteById(int id);
}
