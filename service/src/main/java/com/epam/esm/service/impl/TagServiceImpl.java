package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagConverter;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.util.Validator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private final TagDAO tagDAO;
    private final Validator validator;

    public TagServiceImpl(TagDAO tagDAO, Validator validator) {
        this.tagDAO = tagDAO;
        this.validator = validator;
    }

    @Override
    public TagDTO createTag(String name) {
        validator.matchField(name);
        return TagConverter.toDto(tagDAO.createTag(name));
    }

    @Override
    public List<TagDTO> searchTags() {
        return TagConverter.toDto(tagDAO.searchTags());
    }

    @Override
    public TagDTO searchTag(int id) {
        return TagConverter.toDto(tagDAO.searchTag(id));
    }

    @Override
    public void deleteTag(int id) {
        searchTag(id);
        tagDAO.deleteTag(id);
    }
}
