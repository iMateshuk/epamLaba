package com.epam.esm.dao;

import com.epam.esm.dao.entity.GiftCertificateEntity;

import java.util.List;
import java.util.Map;

public interface GiftCertificateDAO {
  GiftCertificateEntity createGiftCertificate(GiftCertificateEntity giftCertificateEntity);

  List<GiftCertificateEntity> getGiftCertificates();

  GiftCertificateEntity getGiftCertificate(int id);

  List<GiftCertificateEntity> getGiftCertificates(String tagName);

  List<GiftCertificateEntity> getGiftCertificates(Map<String, String> requestedParameters);

  GiftCertificateEntity updateGiftCertificate(GiftCertificateEntity giftCertificateEntity);

  void delGiftCertificate(int id);

  void delGiftCertificateAndTagBundle(int id);

  void addGiftCertificateTag(int GiftCertificateId, String tagName);
}
