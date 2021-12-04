package com.epam.esm.dao;

import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.page.PageDAO;
import com.epam.esm.dao.page.PageParamDAO;

public interface TagDAO {
  TagEntity insertByName(String tagName);

  PageDAO<TagEntity> findAll(PageParamDAO pageParamDAO);

  TagEntity findById(int id);

  TagEntity findByName(String tagName);

  boolean isExistByName(String tagName);

  boolean isExistById(int id);

  void deleteById(int id);
}
