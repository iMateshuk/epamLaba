package com.epam.esm.dao;

import com.epam.esm.dao.entity.TagEntity;

import java.util.List;

public interface TagDAO {

    public TagEntity getTagEntityByName(String tagName);

    public List<TagEntity> getListTag(int id);
}
