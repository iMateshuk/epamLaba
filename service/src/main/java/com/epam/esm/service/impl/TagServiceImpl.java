package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.ConvertEntityToTagDTO;
import com.epam.esm.service.dto.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagDAO tagDAO;


    @Override
    public void createTag(String name) {

        tagDAO.createTag(name);
    }

    @Override
    public List<TagDTO> searchTags() {

        return ConvertEntityToTagDTO.getTagDTO(tagDAO.searchTags());
    }

    @Override
    public TagDTO searchTag(int id) {

        return ConvertEntityToTagDTO.getTagDTO(tagDAO.searchTag(id));
    }

    @Override
    public void deleteTag(int id) {

        tagDAO.deleteTag(id);
    }
}
