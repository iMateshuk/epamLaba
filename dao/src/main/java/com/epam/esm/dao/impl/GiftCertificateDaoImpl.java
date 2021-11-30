package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.util.GiftCertificateSQL;
import com.epam.esm.dao.util.PredicateParameter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.*;

/**
 * Repository Gift-Certificate
 * DAO to MySQL
 *
 * @author Ivan Matsiashuk
 * @version 1.0
 */
@AllArgsConstructor
@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDAO {
  private final EntityManager entityManager;
  private final TagDAO tagDAO;

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
    return entityManager.createQuery(GiftCertificateSQL.SELECT_ALL.getSQL(), GiftCertificateEntity.class)
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
    final String SEARCH_LIKE = "%";
    final String SEARCH = "^SEARCH_.*";
    final String SORT = "^SORT_.*";
    final String JOIN = "^JOIN.*";
    final String JOIN_ATTRIBUTE_NAME = "tags";
    final String SPLIT = ",";

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<GiftCertificateEntity> criteriaQuery = criteriaBuilder.createQuery(GiftCertificateEntity.class);
    Root<GiftCertificateEntity> from = criteriaQuery.from(GiftCertificateEntity.class);
    /*CriteriaQuery<GiftCertificateEntity> select = criteriaQuery.select(from);*/

    List<Predicate> predicates = new ArrayList<>();
    Arrays.stream(PredicateParameter.class.getEnumConstants())
        .filter(item -> item.toString().matches(SEARCH) && parameters.get(item.toString()) != null)
        .forEach(item -> predicates.add(criteriaBuilder.like(from.get(item.getSQL()),
            SEARCH_LIKE + parameters.get(item.toString()) + SEARCH_LIKE)));

    Join<GiftCertificateEntity, TagEntity> join = from.join(JOIN_ATTRIBUTE_NAME, JoinType.INNER);
    Arrays.stream(PredicateParameter.class.getEnumConstants())
        .filter(item -> item.toString().matches(JOIN) && parameters.get(item.toString()) != null)
        .forEach(item -> Arrays.stream(parameters.get(item.toString())
            .split(SPLIT))
            .forEach(element -> predicates.add(criteriaBuilder.like(join.get(item.getSQL()),
            SEARCH_LIKE + element + SEARCH_LIKE))));

    if (!predicates.isEmpty()) {
      criteriaQuery.where(criteriaBuilder.or(predicates.toArray(new Predicate[0])));
    }

    List<Order> orders = new ArrayList<>();
    Arrays.stream(PredicateParameter.class.getEnumConstants())
        .filter(item -> item.toString().matches(SORT) && parameters.get(item.toString()) != null)
        .forEach(item -> {
          if (parameters.get(item.toString()).equalsIgnoreCase(PredicateParameter.ORDER_DESC.getSQL())) {
            orders.add(criteriaBuilder.desc(from.get(item.getSQL())));
          }
          if (parameters.get(item.toString()).equalsIgnoreCase(PredicateParameter.ORDER_ASC.getSQL())) {
            orders.add(criteriaBuilder.asc(from.get(item.getSQL())));
          }
        });

    List<GiftCertificateEntity> entities;
    if (!orders.isEmpty()) {
      entities = entityManager.createQuery(criteriaQuery.orderBy(orders)).getResultList();
    } else {
      entities = entityManager.createQuery(criteriaQuery).getResultList();
    }
    return entities;
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
    final String NAME = "name";
    return entityManager.createQuery(GiftCertificateSQL.SELECT_ALL_BY_NAME.getSQL(), GiftCertificateEntity.class)
        .setParameter(NAME, certificateName).getResultList().stream().findFirst().orElse(null);
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
