package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.util.GiftCertificateSQL;
import com.epam.esm.dao.util.QueryBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDAO {
  private final EntityManager entityManager;
  private final TagDAO tagDAO;
  private final QueryBuilder queryBuilder;

  @Override
  public GiftCertificateEntity insert(GiftCertificateEntity giftCertificate) {
    findAndSetTagId(giftCertificate);
    return entityManager.merge(giftCertificate);
  }

  @Override
  public GiftCertificateEntity findById(int id) {
    return entityManager.find(GiftCertificateEntity.class, id);
  }

  @Override
  public boolean isExistByName(String certificateName) {
    return searchCertificate(certificateName) != null;
  }

  @Override
  public boolean isExistById(int id) {
    return findById(id) != null;
  }

  @Override
  public List<GiftCertificateEntity> findAll(Map<String, String> parameters, int pageNumber, int pageSize) {
    return entityManager.createQuery(queryBuilder.buildQuery(parameters))
        .setFirstResult(pageNumber * pageSize)
        .setMaxResults(pageSize)
        .getResultList();
  }

  @Override
  public long count(Map<String, String> parameters) {
    String query = queryBuilder.buildNativeQuery(GiftCertificateSQL.SELECT_MAIN_SEARCH.getSQL(), parameters);
    return ((Number) entityManager.createNativeQuery(query)
        .getSingleResult()
    ).longValue();
  }

  @Override
  public GiftCertificateEntity update(GiftCertificateEntity giftCertificate) {
    GiftCertificateEntity certificateEntity = findById(giftCertificate.getId());

    String name = giftCertificate.getName();
    if (name != null) {
      certificateEntity.setName(name);
    }
    String description = giftCertificate.getDescription();
    if (description != null) {
      certificateEntity.setDescription(description);
    }
    Float price = giftCertificate.getPrice();
    if (price != null) {
      certificateEntity.setPrice(price);
    }
    Integer duration = giftCertificate.getDuration();
    if (duration != null) {
      certificateEntity.setDuration(duration);
    }
    List<TagEntity> entities = giftCertificate.getTags();
    if (entities != null) {
      findAndSetTagId(giftCertificate);
      certificateEntity.setTags(entities);
    }
    return entityManager.merge(certificateEntity);
  }

  @Override
  public void deleteById(int id) {
    GiftCertificateEntity certificate = findById(id);
    certificate.getTags().forEach(tagEntity -> tagEntity.getCerts().remove(certificate));
    entityManager.remove(certificate);
  }

  private GiftCertificateEntity searchCertificate(String certificateName) {
    final String NAME = "name";
    return entityManager.createQuery(GiftCertificateSQL.SELECT_ALL_BY_NAME.getSQL(), GiftCertificateEntity.class)
        .setParameter(NAME, certificateName)
        .getResultList()
        .stream()
        .findFirst()
        .orElse(null);
  }

  private void findAndSetTagId(GiftCertificateEntity certificate) {
    certificate.getTags()
        .forEach((tagEntity) -> {
          TagEntity entity = tagDAO.findByName(tagEntity.getName());
          if (entity != null) {
            tagEntity.setId(entity.getId());
          }
        });
  }
}
