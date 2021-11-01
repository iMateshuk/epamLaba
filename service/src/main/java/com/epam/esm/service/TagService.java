package com.epam.esm.service;

import com.epam.esm.service.dto.TagDTO;

import java.util.List;

public interface TagService {

    TagDTO createTag(String name);

    List<TagDTO> searchTags();

    TagDTO searchTag(int id);

    void deleteTag(int id);
}
