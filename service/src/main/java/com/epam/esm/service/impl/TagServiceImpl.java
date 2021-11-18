package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagConverter;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.util.ServiceValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TagServiceImpl implements TagService {


    private final TagDAO tagDAO;

    public TagServiceImpl(TagDAO tagDAO) {

        this.tagDAO = tagDAO;
    }


    @Override
    public TagDTO createTag(String name) {

        ServiceValidator.tagNameLengthValidator(name);
        ServiceValidator.tagNameValidator(name);

        return TagConverter.toDto(tagDAO.createTag(name));
    }

    @Override
    public List<TagDTO> searchTags() {

        List<TagDTO> tagDTOs = TagConverter.toDto(tagDAO.searchTags());

        if (ServiceValidator.isListEmpty(tagDTOs)) {

            throw new NoSuchElementException(getClass().getSimpleName() + " exception:tagServ002");
        }

        return tagDTOs;
    }

    @Override
    public TagDTO searchTag(int id) {

        ServiceValidator.isPositiveInteger(id);
        ServiceValidator.isZeroInteger(id);

        TagDTO tagDTO = TagConverter.toDto(tagDAO.searchTag(id));

        if (ServiceValidator.stringNullOrEmpty(tagDTO.getName())) {

            throw new NoSuchElementException(getClass().getSimpleName() + " exception:tagServ003");
        }

        return tagDTO;
    }

    @Override
    public void deleteTag(int id) {

        ServiceValidator.isPositiveInteger(id);
        ServiceValidator.isZeroInteger(id);

        tagDAO.deleteTag(id);
    }
}
