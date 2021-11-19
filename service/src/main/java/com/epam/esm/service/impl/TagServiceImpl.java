package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.ErrorDto;
import com.epam.esm.service.dto.TagConverter;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.util.ServiceValidator;
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
        List<TagDTO> tagDTOs = TagConverter.toDto(tagDAO.searchTags());
        if (ServiceValidator.isListEmpty(tagDTOs)) {
            throw new ServiceException(new ErrorDto("tag.service.search.tags"), 20);
        }
        return tagDTOs;
    }

    @Override
    public TagDTO searchTag(int id) {
        TagDTO tagDTO = TagConverter.toDto(tagDAO.searchTag(id));
        if(tagDTO.getName() == null) {
            throw new ServiceException(new ErrorDto("tag.service.search.tagId"), 21);
        }
        return tagDTO;
    }

    @Override
    public void deleteTag(int id) {
        searchTag(id);
        tagDAO.deleteTag(id);
    }
}
