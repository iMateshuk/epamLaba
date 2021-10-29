package com.epam.esm.dao;

import com.epam.esm.dao.entity.GiftCertificateEntity;

import java.util.List;

public interface GiftCertificateDAO {

    public List<GiftCertificateEntity> getListGiftCertificate(String tagName);

}
