package com.epam.esm.dao;

import com.epam.esm.dao.entity.GiftCertificateEntity;

import java.util.List;

public interface GiftCertificateDAO {

    GiftCertificateEntity createGiftCertificate(GiftCertificateEntity giftCertificateEntity);

    List<GiftCertificateEntity> searchGiftCertificates();

    GiftCertificateEntity searchGiftCertificate(int id);

    List<GiftCertificateEntity> getListGiftCertificates(String tagName);

    GiftCertificateEntity updateGiftCertificate(GiftCertificateEntity giftCertificateEntity);

    void delGiftCertificate(int id);

}
