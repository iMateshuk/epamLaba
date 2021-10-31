package com.epam.esm.dao;

import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.entity.TagEntity;

import java.util.List;

public interface CommonDAO {

    public List<GiftCertificateEntity> getListGiftCertificate(String tagName);

    public List<TagEntity> getListTag(int id);
}
