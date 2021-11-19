package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.config.TagMapper;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.util.TagSQL;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagDB implements TagDAO {
  private final JdbcTemplate jdbcTemplate;
  private final TagMapper tagMapper;

  public TagDB(JdbcTemplate jdbcTemplate, TagMapper tagMapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.tagMapper = tagMapper;
  }

  @Override
  public TagEntity createTag(String tagName) {
    jdbcTemplate.update(TagSQL.INSERT_TAG.getSQL(), tagName);
    return searchTag(tagName);
  }

  @Override
  public List<TagEntity> searchTags() {
    return jdbcTemplate.query(TagSQL.SELECT_ALL.getSQL(), tagMapper);
  }

  @Override
  public List<TagEntity> getListTag(int giftCertificateId) {
    return jdbcTemplate.query(TagSQL.SELECT_W_GC_ID.getSQL(), tagMapper, giftCertificateId);
  }

  @Override
  public TagEntity searchTag(int id) {
    return jdbcTemplate.queryForObject(TagSQL.SELECT_ALL_W_ID.getSQL(), tagMapper, id);
  }

  @Override
  public TagEntity searchTag(String tagName) {
    return jdbcTemplate.queryForObject(TagSQL.SELECT_ALL_W_NAME.getSQL(), tagMapper, tagName);
  }

  @Override
  public boolean isTagExist(String tagName) {
    Integer count = jdbcTemplate.queryForObject(TagSQL.SELECT_COUNT_W_NAME.getSQL(), Integer.class, tagName);
    return count != null && count > 0;
  }

  @Override
  public void deleteTag(int id) {
    jdbcTemplate.update(TagSQL.DEL_DB_CASCADE_W_ID.getSQL(), id);
  }
}
