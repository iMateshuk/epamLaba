package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.util.TagSQL;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Repository Tag
 * DAO to MySQL
 *
 * @author Ivan Matsiashuk
 * @version 1.0
 */
@Repository
public class TagDB implements TagDAO {

  @PersistenceContext
  private EntityManager entityManager;

  /**
   * Create Tag entry in the table
   *
   * @param tagName Tag field.
   * @return Tag entity.
   * <p>
   * The method can throw DuplicateKeyException
   */
  @Override
  public TagEntity insertTag(String tagName) {
    entityManager.persist(new TagEntity(tagName));
    return findTag(tagName);
  }

  /**
   * @return List of Tag
   */
  @Override
  public List<TagEntity> findAllTags() {
    return entityManager.createQuery(TagSQL.QL_SELECT_ALL.getSQL(), TagEntity.class).getResultList();
  }

  /**
   * @param id PK Tag field.
   * @return Tag entity
   * <p>
   * The method can throw EmptyResultDataAccessException
   */
  @Override
  public TagEntity findTag(int id) {
    return entityManager.find(TagEntity.class, id);
  }

  /**
   * @param tagName Tag field.
   * @return Tag entity or null.
   */
  @Override
  public TagEntity findTag(String tagName) {
    return entityManager.createQuery(TagSQL.QL_SELECT_ALL_W_NAME.getSQL(), TagEntity.class)
        .setParameter("name", tagName).getResultList().stream().findFirst().orElse(null);
  }

  /**
   * @param tagName Tag field.
   * @return true when find name.
   */
  @Override
  public boolean isTagExist(String tagName) {
    return findTag(tagName) != null;
  }

  /**
   * @param id PK Tag field.
   * @return true when find id.
   */
  @Override
  public boolean isTagExist(int id) {
    return entityManager.contains(findTag(id));
  }

  /**
   * @param id PK Tag field.
   */
  @Override
  public void deleteTag(int id) {
    entityManager.remove(findTag(id));
  }
}
