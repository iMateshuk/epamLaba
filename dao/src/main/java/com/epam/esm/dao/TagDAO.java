package com.epam.esm.dao;

import com.epam.esm.dao.entity.TagEntity;

import java.util.List;

public interface TagDAO {
  TagEntity insertTag(String tagName);

  List<TagEntity> findAllTags();

  TagEntity findTag(int id);

  TagEntity findTag(String tagName);

  boolean isTagExist(String tagName);

  boolean isTagExist(int id);

  void deleteTag(int id);
}
