package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.page.PageDAO;
import com.epam.esm.dao.page.PageParamDAO;
import com.epam.esm.dao.page.PageParamFill;
import com.epam.esm.dao.util.GiftCertificateSQL;
import com.epam.esm.dao.util.QueryWork;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

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
  private final QueryWork queryWork;
  private final PageParamFill pageFill;

  /**
   * @param giftCertificate insert in table
   * @return GiftCertificateEntity
   * <p>
   * The method can throw IncorrectResultSizeDataAccessException
   */
  @Override
  public GiftCertificateEntity insert(GiftCertificateEntity giftCertificate) {
    findAndSetTagId(giftCertificate);
    return entityManager.merge(giftCertificate);
  }

  /**
   * @return List of GiftCertificateEntity
   */
  @Override
  public PageDAO<GiftCertificateEntity> findAll(PageParamDAO pageParamDAO) {
    List<GiftCertificateEntity> certificates =
        queryWork.executeQuery(pageParamDAO, GiftCertificateSQL.SELECT_ALL.getSQL(), GiftCertificateEntity.class);
    pageFill.fillingPage(pageParamDAO, GiftCertificateSQL.COUNT_ALL.getSQL());
    return new PageDAO<>(certificates, pageParamDAO);
  }

  @Override
  public PageDAO<GiftCertificateEntity> findById(int id, PageParamDAO pageParamDAO) {
    List<GiftCertificateEntity> certificates = List.of(entityManager.find(GiftCertificateEntity.class, id));
    pageFill.fillingPage(pageParamDAO, GiftCertificateSQL.COUNT_ID.getSQL(), id);
    return new PageDAO<>(certificates, pageParamDAO);
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
  public PageDAO<GiftCertificateEntity> findAllWithParam(Map<String, String> parameters, PageParamDAO pageParamDAO) {
    List<GiftCertificateEntity> certificates =
        queryWork.executeQuery(pageParamDAO, queryWork.buildQuery(parameters, GiftCertificateEntity.class, TagEntity.class));
    pageFill.fillingPageNativeQuery(pageParamDAO, GiftCertificateSQL.SELECT_MAIN_SEARCH.getSQL(), parameters);
    return new PageDAO<>(certificates, pageParamDAO);
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
