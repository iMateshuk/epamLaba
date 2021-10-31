package com.epam.esm.dao;

import com.epam.esm.dao.entity.GiftCertificateEntity;

import java.util.List;

public interface GiftCertificateDAO {

    public void createGiftCertificate(GiftCertificateEntity giftCertificateEntity);

    public List<GiftCertificateEntity> searchGiftCertificates();

    public GiftCertificateEntity searchGiftCertificate(int id);

    public void updateGiftCertificate(GiftCertificateEntity giftCertificateEntity);

    public void delGiftCertificate(int id);

}
