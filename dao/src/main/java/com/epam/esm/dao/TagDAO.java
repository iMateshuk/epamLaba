package com.epam.esm.dao;

import com.epam.esm.dao.entity.TagEntity;

import java.util.List;

public interface TagDAO {

    public void createTag(String name);

    public List<TagEntity> searchTags();

    public TagEntity searchTag(int id);

    public void deleteTag(int id);
}
