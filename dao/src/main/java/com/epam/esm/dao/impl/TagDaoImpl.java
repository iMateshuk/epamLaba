package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.util.TagSQL;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@AllArgsConstructor
@Repository
public class TagDaoImpl implements TagDAO {
  private final EntityManager entityManager;

  @Override
  public TagEntity insertByName(String tagName) {
    entityManager.persist(new TagEntity(tagName));
    return findByName(tagName);
  }

  @Override
  public List<TagEntity> findAll(int pageNumber, int pageSize) {
    return entityManager.createQuery(TagSQL.SELECT_ALL.getSQL(), TagEntity.class)
        .setFirstResult(pageNumber * pageSize)
        .setMaxResults(pageSize)
        .getResultList();
  }

  @Override
  public long count() {
    return entityManager.createQuery(TagSQL.COUNT_ALL.getSQL(), Long.class).getSingleResult();
  }

  @Override
  public TagEntity findById(int id) {
    return entityManager.find(TagEntity.class, id);
  }

  @Override
  public TagEntity findByName(String tagName) {
    final String NAME = "name";
    return entityManager.createQuery(TagSQL.SELECT_TAG_BY_NAME.getSQL(), TagEntity.class)
        .setParameter(NAME, tagName).getResultList().stream().findFirst().orElse(null);
  }

  @Override
  public boolean isExistByName(String tagName) {
    return findByName(tagName) != null;
  }

  @Override
  public boolean isExistById(int id) {
    return findById(id) != null;
  }

  @Override
  public void deleteById(int id) {
    TagEntity tagEntity = findById(id);
    tagEntity.getCerts().forEach(certificate -> certificate.getTags().remove(tagEntity));
    entityManager.remove(tagEntity);
  }
}
