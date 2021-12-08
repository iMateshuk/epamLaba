package com.epam.esm.dao;

import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.page.PageData;

import java.util.List;
import java.util.Map;

public interface GiftCertificateDAO {
  /**
   *
   * @param giftCertificate
   * @return new entity of certificate
   */
  GiftCertificateEntity insert(GiftCertificateEntity giftCertificate);

  /**
   *
   * @param id
   * @return entity equal id
   */
  GiftCertificateEntity findById(int id);

  /**
   *
   * @param certificateName
   * @return true if certificateName exist
   */
  boolean isExistByName(String certificateName);

  /**
   *
   * @param id
   * @return true if certificate whith id exist
   */
  boolean isExistById(int id);

  /**
   *
   * @param parameters
   * @param pageData
   * @return list of entity
   */
  List<GiftCertificateEntity> findAll(Map<String, String> parameters, PageData pageData);

  /**
   *
   * @param parameters
   * @return (Long) count
   */
  long count(Map<String, String> parameters);

  /**
   *
   * @param giftCertificate
   * @return updated entity
   */
  GiftCertificateEntity update(GiftCertificateEntity giftCertificate);

  /**
   *
   * @param id
   */
  void deleteById(int id);
}
