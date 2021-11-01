package com.epam.esm.dao;

import com.epam.esm.dao.entity.TagEntity;

import java.util.List;

public interface TagDAO {

    TagEntity createTag(String tagName);

    List<TagEntity> searchTags();

    List<TagEntity> getListTag(int id);

    TagEntity searchTag(int id);

    TagEntity searchTag(String tagName);

    boolean isTagExist(String tagName);

    void deleteTag(int id);
}
