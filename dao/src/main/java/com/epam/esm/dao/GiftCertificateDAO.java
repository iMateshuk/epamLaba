package com.epam.esm.dao;

import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.page.PageData;

import java.util.List;
import java.util.Map;

public interface GiftCertificateDAO {
  GiftCertificateEntity insert(GiftCertificateEntity giftCertificate);

  GiftCertificateEntity findById(int id);

  boolean isExistByName(String certificateName);

  boolean isExistById(int id);

  List<GiftCertificateEntity> findAll(Map<String, String> parameters, PageData pageData);

  long count(Map<String, String> parameters);

  GiftCertificateEntity update(GiftCertificateEntity giftCertificate);

  void deleteById(int id);
}
