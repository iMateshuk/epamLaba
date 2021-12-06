package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.page.Page;
import com.epam.esm.dao.page.PageParam;
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
  public Page<TagEntity> findAll(PageParam pageParam) {
    int pageNumber = pageParam.getNumber();
    int pageSize = pageParam.getSize();
    List<TagEntity> tags = entityManager.createQuery(TagSQL.SELECT_ALL.getSQL(), TagEntity.class)
        .setFirstResult(pageNumber * pageSize)
        .setMaxResults(pageSize)
        .getResultList();
    Long count = entityManager.createQuery(TagSQL.COUNT_ALL.getSQL(), Long.class).getSingleResult();
    fillPage(pageParam, count);
    return new Page<>(tags, pageParam);
  }

  @Override
  public Page<TagEntity> findById(int id, PageParam pageParam) {
    List<TagEntity> tags = List.of(entityManager.find(TagEntity.class, id));
    Long count = entityManager.createQuery(TagSQL.COUNT_ID.getSQL(), Long.class).setParameter("id", id).getSingleResult();
    fillPage(pageParam, count);
    return new Page<>(tags, pageParam);
  }

  @Override
  public TagEntity findByName(String tagName) {
    final String NAME = "name";
    return entityManager.createQuery(TagSQL.SELECT_ALL_BY_NAME.getSQL(), TagEntity.class)
        .setParameter(NAME, tagName).getResultList().stream().findFirst().orElse(null);
  }

  @Override
  public boolean isExistByName(String tagName) {
    return findByName(tagName) != null;
  }

  @Override
  public boolean isExistById(int id) {
    return entityManager.contains(findById(id));
  }

  @Override
  public void deleteById(int id) {
    TagEntity tagEntity = findById(id);
    tagEntity.getCerts().forEach(certificate -> certificate.getTags().remove(tagEntity));
    entityManager.remove(tagEntity);
  }

  private TagEntity findById(int id) {
    return entityManager.find(TagEntity.class, id);
  }

  private void fillPage(PageParam pageParam, Long count) {
    pageParam.setTotalElements(count);
    pageParam.setTotalPages(count / pageParam.getSize());
  }
}
