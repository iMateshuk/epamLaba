package com.epam.esm.dao;

import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.entity.TagEntity;

import java.util.List;

public interface DAO {

    public String list();

    public List<GiftCertificateEntity> getListGiftCertificate();

    public List<GiftCertificateEntity> getListGiftCertificate(String tagName);

    public TagEntity getTagEntityByName(String tagName);

    public List<TagEntity> getListTag();

    public List<TagEntity> getListTag(int id);
}
