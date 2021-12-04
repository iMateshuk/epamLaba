package com.epam.esm.dao;

import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.page.PageDAO;
import com.epam.esm.dao.page.PageParamDAO;

import java.util.Map;

public interface GiftCertificateDAO {
  GiftCertificateEntity insert(GiftCertificateEntity giftCertificate);

  PageDAO<GiftCertificateEntity> findAll(PageParamDAO pageParamDAO);

  GiftCertificateEntity findById(int id);

  boolean isExistByName(String certificateName);

  boolean isExistById(int id);

  PageDAO<GiftCertificateEntity> findAllWithParam(Map<String, String> parameters, PageParamDAO pageParamDAO);

  GiftCertificateEntity update(GiftCertificateEntity giftCertificate);

  void deleteById(int id);
}
