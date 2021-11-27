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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
  public GiftCertificateEntity insert(GiftCertificateEntity giftCertificate) {
    findAndSetTagId(giftCertificate);
    entityManager.persist(giftCertificate);
    return findById(giftCertificate.getId());
  }

  /**
   * @return List of GiftCertificateEntity
   */
  @Override
  public List<GiftCertificateEntity> findAll() {
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
  public GiftCertificateEntity findById(int id) {
    return entityManager.find(GiftCertificateEntity.class, id);
  }

  /**
   * @param certificateName uniq name
   * @return true when find certificateName
   */
  @Override
  public boolean isExistByName(String certificateName) {
    return searchCertificate(certificateName) != null;
  }

  /**
   * @param id table PK
   * @return true when find certificateName
   */
  @Override
  public boolean isExistById(int id) {
    return entityManager.contains(findById(id));
  }

  /**
   * @param parameters Map of parameters.
   * @return List of GiftCertificateEntity
   */
  @Override
  public List<GiftCertificateEntity> findAllWithParam(Map<String, String> parameters) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<GiftCertificateEntity> criteriaQuery = criteriaBuilder.createQuery(GiftCertificateEntity.class);
    Root<GiftCertificateEntity> from = criteriaQuery.from(GiftCertificateEntity.class);
    CriteriaQuery<GiftCertificateEntity> select = criteriaQuery.select(from);

    criteriaQuery.orderBy(criteriaBuilder.asc(from.get("name")),
        criteriaBuilder.desc(from.get("id")));

    final String sql = QueryCreator.buildSql(parameters);
    QueryCreator.removeKeyMatchSort(parameters);
    return jdbcTemplate.query(sql, giftCertificateMapper, parameters.values().toArray());
  }

  /**
   * @param giftCertificate update some collum in table.
   * @return GiftCertificateEntity
   * <p>
   * The method can throw EmptyResultDataAccessException
   */
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

  /**
   * @param id PK.
   */
  @Override
  public void deleteById(int id) {
    GiftCertificateEntity certificate = findById(id);
    certificate.getTags().forEach(tagEntity -> tagEntity.getCerts().remove(certificate));
    entityManager.remove(certificate);
  }

  private GiftCertificateEntity searchCertificate(String certificateName) {
    return entityManager.createQuery(GiftCertificateSQL.QL_SELECT_ALL_W_NAME.getSQL(), GiftCertificateEntity.class)
        .setParameter("name", certificateName).getResultList().stream().findFirst().orElse(null);
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
