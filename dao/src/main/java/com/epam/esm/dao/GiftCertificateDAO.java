package com.epam.esm.dao;

import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.page.Page;
import com.epam.esm.dao.page.PageParam;

import java.util.Map;

public interface GiftCertificateDAO {
  GiftCertificateEntity insert(GiftCertificateEntity giftCertificate);

  Page<GiftCertificateEntity> findById(int id, PageParam pageParam);

  GiftCertificateEntity findById(int id);

  boolean isExistByName(String certificateName);

  boolean isExistById(int id);

  Page<GiftCertificateEntity> findAll(Map<String, String> parameters, PageParam pageParam);

  GiftCertificateEntity update(GiftCertificateEntity giftCertificate);

  void deleteById(int id);
}
