package com.epam.esm.dao;

import com.epam.esm.dao.entity.TagEntity;

import java.util.List;

public interface TagDAO {
  TagEntity createTag(String tagName);

  List<TagEntity> searchTags();

  List<TagEntity> getListTag(int giftCertificateId);

  TagEntity searchTag(int id);

  TagEntity searchTag(String tagName);

  boolean isTagExist(String tagName);

  boolean isTagExist(int id);

  void deleteTag(int id);
}
