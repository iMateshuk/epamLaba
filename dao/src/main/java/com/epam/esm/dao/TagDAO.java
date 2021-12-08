package com.epam.esm.dao;

import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.page.PageData;

import java.util.List;

public interface TagDAO {
  /**
   *
   * @param tagName
   * @return new tagEntity
   */
  TagEntity insertByName(String tagName);

  /**
   *
   * @param pageData
   * @return list of tagEntity
   */
  List<TagEntity> findAll(PageData pageData);

  /**
   *
   * @return count of all tagEntity records
   */
  long count();

  /**
   *
   * @param id
   * @return tagEntity found by id
   */
  TagEntity findById(int id);

  /**
   *
   * @param tagName
   * @return tagEntity found by name
   */
  TagEntity findByName(String tagName);

  /**
   *
   * @param tagName
   * @return true if tagEntity exist
   */
  boolean isExistByName(String tagName);

  /**
   *
   * @param id
   * @return true if tagEntity exist
   */
  boolean isExistById(int id);

  /**
   *
   * @param id
   */
  void deleteById(int id);
}
