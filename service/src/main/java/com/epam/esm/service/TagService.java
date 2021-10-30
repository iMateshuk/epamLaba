package com.epam.esm.service;

import com.epam.esm.service.dto.TagDTO;

import java.util.List;

public interface TagService {

    public void createTag(String name);

    public List<TagDTO> searchTags();

    public TagDTO searchTag(int id);

    public void deleteTag(int id);
}
