package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagConverter;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.util.Validator;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  Service Tag
 *  Use for business logic of APP
 *
 *  @author Ivan Matsiashuk
 *  @version 1.0
 */
@Service
public class TagServiceImpl implements TagService {
  private final TagDAO tagDAO;
  private final Validator validator;

  public TagServiceImpl(TagDAO tagDAO, Validator validator) {
    this.tagDAO = tagDAO;
    this.validator = validator;
  }

  /**
   *
   * @param name of new Tag.
   * @return TagDTO
   *
   * The method can throw ValidationException extends RuntimeException
   */
  @Override
  public TagDTO createTag(String name) {
    validator.matchField(name);
    return TagConverter.toDto(tagDAO.createTag(name));
  }

  /**
   *
   * @return List of TagDTO
   */
  @Override
  public List<TagDTO> searchTags() {
    return TagConverter.toDto(tagDAO.searchTags());
  }

  /**
   *
   * @param id Tag field.
   * @return TagDTO
   */
  @Override
  public TagDTO searchTag(int id) {
    return TagConverter.toDto(tagDAO.searchTag(id));
  }

  /**
   *
   * @param id Tag field.
   */
  @Override
  public void deleteTag(int id) {
    tagDAO.deleteTag(id);
  }
}
