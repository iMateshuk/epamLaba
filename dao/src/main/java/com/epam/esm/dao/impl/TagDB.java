package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.util.TagSQL;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
  private final EntityManager entityManager;

  public TagDB(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  /**
   * Create Tag entry in the table
   *
   * @param tagName Tag field.
   * @return Tag entity.
   * <p>
   * The method can throw DuplicateKeyException
   */
  @Override
  public TagEntity insertByName(String tagName) {
    entityManager.persist(new TagEntity(tagName));
    return findByName(tagName);
  }

  /**
   * @return List of Tag
   */
  @Override
  public List<TagEntity> findAll() {
    return entityManager.createQuery(TagSQL.QL_SELECT_ALL.getSQL(), TagEntity.class).getResultList();
  }

  /**
   * @param id PK Tag field.
   * @return Tag entity
   * <p>
   * The method can throw EmptyResultDataAccessException
   */
  @Override
  public TagEntity findById(int id) {
    return entityManager.find(TagEntity.class, id);
  }

  /**
   * @param tagName Tag field.
   * @return Tag entity or null.
   */
  @Override
  public TagEntity findByName(String tagName) {
    return entityManager.createQuery(TagSQL.QL_SELECT_ALL_W_NAME.getSQL(), TagEntity.class)
        .setParameter("name", tagName).getResultList().stream().findFirst().orElse(null);
  }

  /**
   * @param tagName Tag field.
   * @return true when find name.
   */
  @Override
  public boolean isExistByName(String tagName) {
    return findByName(tagName) != null;
  }

  /**
   * @param id PK Tag field.
   * @return true when find id.
   */
  @Override
  public boolean isExistById(int id) {
    return entityManager.contains(findById(id));
  }

  /**
   * @param id PK Tag field.
   */
  @Override
  public void deleteById(int id) {
    TagEntity tagEntity = findById(id);
    tagEntity.getCerts().forEach(certificate -> certificate.getTags().remove(tagEntity));
    entityManager.remove(tagEntity);
  }
}
