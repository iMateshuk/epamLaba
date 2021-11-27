package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.config.GiftCertificateMapper;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.util.GiftCertificateSQL;
import com.epam.esm.dao.util.QueryCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Repository Gift-Certificate
 * DAO to MySQL
 *
 * @author Ivan Matsiashuk
 * @version 1.0
 */
@Repository
public class GiftCertificateDB implements GiftCertificateDAO {
  private final JdbcTemplate jdbcTemplate;
  private final GiftCertificateMapper giftCertificateMapper;
  private final EntityManager entityManager;
  private final TagDAO tagDAO;

  public GiftCertificateDB(JdbcTemplate jdbcTemplate,
                           GiftCertificateMapper giftCertificateMapper,
                           EntityManager entityManager,
                           TagDAO tagDAO) {
    this.jdbcTemplate = jdbcTemplate;
    this.giftCertificateMapper = giftCertificateMapper;
    this.entityManager = entityManager;
    this.tagDAO = tagDAO;
  }

  /**
   * @param giftCertificate insert in table
   * @return GiftCertificateEntity
   * <p>
   * The method can throw IncorrectResultSizeDataAccessException
   */
  @Override
  public GiftCertificateEntity insertCertificate(GiftCertificateEntity giftCertificate) {
    findAndSetTagId(giftCertificate);
    entityManager.persist(giftCertificate);
    return findCertificate(giftCertificate.getId());
  }

  /**
   * @return List of GiftCertificateEntity
   */
  @Override
  public List<GiftCertificateEntity> findAllCertificates() {
    return entityManager.createQuery(GiftCertificateSQL.QL_SELECT_ALL.getSQL(), GiftCertificateEntity.class)
        .getResultList();
  }

  /**
   * @param id PK.
   * @return GiftCertificateEntity
   * <p>
   * The method can throw EmptyResultDataAccessException
   */
  @Override
  public GiftCertificateEntity findCertificate(int id) {
    return entityManager.find(GiftCertificateEntity.class, id);
  }

  /**
   * @param certificateName uniq name
   * @return true when find certificateName
   */
  @Override
  public boolean isExistCertificate(String certificateName) {
    return searchCertificate(certificateName) != null;
  }

  /**
   * @param id table PK
   * @return true when find certificateName
   */
  @Override
  public boolean isExistCertificate(int id) {
    return entityManager.contains(findCertificate(id));
  }

  /**
   * @param requestedParameters Map of parameters.
   * @return List of GiftCertificateEntity
   */
  @Override
  public List<GiftCertificateEntity> findAllCertificates(Map<String, String> requestedParameters) {
    final String sql = QueryCreator.buildSql(requestedParameters);
    QueryCreator.removeKeyMatchSort(requestedParameters);
    return jdbcTemplate.query(sql, giftCertificateMapper, requestedParameters.values().toArray());
  }

  /**
   * @param giftCertificate update some collum in table.
   * @return GiftCertificateEntity
   * <p>
   * The method can throw EmptyResultDataAccessException
   */
  @Override
  public GiftCertificateEntity updateCertificate(GiftCertificateEntity giftCertificate) {
    GiftCertificateEntity certificateEntity = findCertificate(giftCertificate.getId());

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

  /**
   * @param id PK.
   */
  @Override
  public void deleteCertificate(int id) {
    GiftCertificateEntity certificate = findCertificate(id);
    certificate.getTags().forEach(tagEntity -> tagEntity.getCerts().remove(certificate));
    entityManager.remove(certificate);
  }

  private GiftCertificateEntity searchCertificate(String certificateName) {
    return entityManager.createQuery(GiftCertificateSQL.QL_SELECT_ALL_W_NAME.getSQL(), GiftCertificateEntity.class)
        .setParameter("name", certificateName).getResultList().stream().findFirst().orElse(null);
  }

  private List<Object> prepareObjects(GiftCertificateEntity giftCertificate) {
    List<Object> params = new ArrayList<>();

    params.add(giftCertificate.getName());
    params.add(giftCertificate.getDescription());
    params.add(giftCertificate.getPrice());
    params.add(giftCertificate.getDuration());
    return params;
  }

  private void findAndSetTagId(GiftCertificateEntity certificate) {
    certificate.getTags()
        .forEach((tagEntity) -> {
          TagEntity entity = tagDAO.findTag(tagEntity.getName());
          if (entity != null) {
            tagEntity.setId(entity.getId());
          }
        });
  }
}
