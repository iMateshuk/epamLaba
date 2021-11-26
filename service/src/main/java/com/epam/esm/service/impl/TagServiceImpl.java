package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.ErrorDto;
import com.epam.esm.service.dto.TagConverter;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.exception.ServiceConflictException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.util.Validator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Tag
 * Use for business logic of APP
 *
 * @author Ivan Matsiashuk
 * @version 1.0
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
   * @param name of new Tag.
   * @return TagDTO
   * <p>
   * The method can throw ServiceConflictException extends RuntimeException
   */
  @Transactional
  @Override
  public TagDTO insertTag(String name) {
    validator.matchField(name);
    if (tagDAO.isTagExist(name)) {
      throw new ServiceConflictException(new ErrorDto("tag.create.error", name), 201);
    }
    return TagConverter.toDto(tagDAO.insertTag(name));
  }

  /**
   * @return List of TagDTO
   */
  @Transactional
  @Override
  public List<TagDTO> findAllTags() {
    return TagConverter.toDto(tagDAO.findAllTags());
  }

  /**
   * @param id Tag field.
   * @return TagDTO
   * <p>
   * The method can throw ServiceException extends RuntimeException
   */
  @Transactional
  @Override
  public TagDTO findTag(int id) {
    if (!tagDAO.isTagExist(id)) {
      throw new ServiceException(new ErrorDto("tag.search.error", id), 203);
    }
    return TagConverter.toDto(tagDAO.findTag(id));
  }

  /**
   * @param id Tag field.
   * <p>
   * The method can throw ServiceException extends RuntimeException
   */
  @Transactional
  @Override
  public void deleteTag(int id) {
    if (!tagDAO.isTagExist(id)) {
      throw new ServiceException(new ErrorDto("tag.delete.error", id), 204);
    }
    tagDAO.deleteTag(id);
  }
}
