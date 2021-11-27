package com.epam.esm.dao;

import com.epam.esm.dao.entity.TagEntity;

import java.util.List;

public interface TagDAO {
  TagEntity insertByName(String tagName);

  List<TagEntity> findAll();

  TagEntity findById(int id);

  TagEntity findByName(String tagName);

  boolean isExistByName(String tagName);

  boolean isExistById(int id);

  void deleteById(int id);
}
