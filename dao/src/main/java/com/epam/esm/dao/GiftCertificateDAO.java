package com.epam.esm.dao;

import com.epam.esm.dao.entity.GiftCertificateEntity;

import java.util.List;
import java.util.Map;

public interface GiftCertificateDAO {
  GiftCertificateEntity insert(GiftCertificateEntity giftCertificate);

  List<GiftCertificateEntity> findAll();

  GiftCertificateEntity findById(int id);

  boolean isExistByName(String certificateName);

  boolean isExistById(int id);

  List<GiftCertificateEntity> findAllWithParam(Map<String, String> requestedParameters);

  GiftCertificateEntity update(GiftCertificateEntity giftCertificate);

  void deleteById(int id);
}
