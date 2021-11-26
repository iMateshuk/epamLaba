package com.epam.esm.dao;

import com.epam.esm.dao.entity.GiftCertificateEntity;

import java.util.List;
import java.util.Map;

public interface GiftCertificateDAO {
  GiftCertificateEntity insertCertificate(GiftCertificateEntity giftCertificate);

  List<GiftCertificateEntity> findAllCertificates();

  GiftCertificateEntity findCertificate(int id);

  boolean isExistCertificate(String certificateName);

  boolean isExistCertificate(int id);

  List<GiftCertificateEntity> findAllCertificates(Map<String, String> requestedParameters);

  GiftCertificateEntity updateCertificate(GiftCertificateEntity giftCertificate);

  void deleteCertificate(int id);
}
