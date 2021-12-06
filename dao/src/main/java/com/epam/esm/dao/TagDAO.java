package com.epam.esm.dao;

import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.page.Page;
import com.epam.esm.dao.page.PageParam;

public interface TagDAO {
  TagEntity insertByName(String tagName);

  Page<TagEntity> findAll(PageParam pageParam);

  Page<TagEntity> findById(int id, PageParam pageParam);

  TagEntity findByName(String tagName);

  boolean isExistByName(String tagName);

  boolean isExistById(int id);

  void deleteById(int id);
}
