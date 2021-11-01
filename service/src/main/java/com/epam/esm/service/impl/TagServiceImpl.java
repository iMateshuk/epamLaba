package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagConverter;
import com.epam.esm.service.dto.TagDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {


    private final TagDAO tagDAO;

    public TagServiceImpl (TagDAO tagDAO){

        this.tagDAO = tagDAO;
    }


    @Override
    public TagDTO createTag(String name) {

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

        tagDAO.deleteTag(id);
    }
}
