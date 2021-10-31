package com.epam.esm.dao;

import com.epam.esm.dao.entity.TagEntity;

import java.util.List;

public interface TagDAO {

    public void createTag(String tagName);

    public List<TagEntity> searchTags();

    public TagEntity searchTag(int id);

    public TagEntity searchTag(String tagName);

    public boolean isTagExist(String tagName);

    public void deleteTag(int id);
}
