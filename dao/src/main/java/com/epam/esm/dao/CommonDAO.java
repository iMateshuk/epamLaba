package com.epam.esm.dao;

import com.epam.esm.dao.entity.GiftCertEntity;
import com.epam.esm.dao.entity.TagEntity;

import java.util.List;

public interface CommonDAO {

    public List<GiftCertEntity> getListGiftCertificate(String tagName);

    public TagEntity getTagEntityByName(String tagName);

    public List<TagEntity> getListTag(int id);
}
