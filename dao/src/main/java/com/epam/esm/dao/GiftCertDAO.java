package com.epam.esm.dao;

import com.epam.esm.dao.entity.GiftCertEntity;

import java.util.List;

public interface GiftCertDAO {

    public void createGiftCert(GiftCertEntity giftCertEntity);

    public List<GiftCertEntity> searchGiftCerts();

    public GiftCertEntity searchGiftCert(int id);

    public void updateGiftCert(GiftCertEntity giftCertEntity);

    public void delGiftCert(int id);

}
