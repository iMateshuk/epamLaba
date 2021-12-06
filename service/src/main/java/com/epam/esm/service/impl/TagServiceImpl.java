package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.page.Page;
import com.epam.esm.dao.page.PageParam;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.ErrorDTO;
import com.epam.esm.service.dto.PageDTO;
import com.epam.esm.service.dto.PageParamDTO;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.exception.ServiceConflictException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.page.PageConvertorDTO;
import com.epam.esm.service.util.ServiceConvertor;
import com.epam.esm.service.util.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Tag
 * Use for business logic of APP
 *
 * @author Ivan Matsiashuk
 * @version 1.0
 */
@AllArgsConstructor
@Service
public class TagServiceImpl implements TagService {
  private final TagDAO tagDAO;
  private final Validator validator;
  private final ServiceConvertor convertor;
  private final PageConvertorDTO convertorDTO;

  /**
   * @param name of new Tag.
   * @return TagDTO
   * <p>
   * The method can throw ServiceConflictException extends RuntimeException
   */
  @Transactional
  @Override
  public TagDTO insertByName(String name) {
    validator.matchField(name);
    if (tagDAO.isExistByName(name)) {
      throw new ServiceConflictException(new ErrorDTO("tag.create.error", name), 201);
    }
    return convertor.toTarget(tagDAO.insertByName(name), TagDTO.class);
  }

  /**
   * @return List of TagDTO
   */
  @Transactional
  @Override
  public PageDTO<TagDTO> findAll(PageParamDTO pageParamDTO) {
    Page<TagEntity> page = tagDAO.findAll(convertor.toTarget(pageParamDTO, PageParam.class));
    return convertorDTO.toDto(page, TagDTO.class);
  }

  /**
   * @param id Tag field.
   * @return TagDTO
   * <p>
   * The method can throw ServiceException extends RuntimeException
   */
  @Transactional
  @Override
  public PageDTO<TagDTO> findById(Integer id, PageParamDTO pageParamDTO) {
    if (!tagDAO.isExistById(id)) {
      throw new ServiceException(new ErrorDTO("tag.search.error", id), 203);
    }
    Page<TagEntity> page = tagDAO.findById(id, convertor.toTarget(pageParamDTO, PageParam.class));
    return convertorDTO.toDto(page, TagDTO.class);
  }

  /**
   * @param id Tag field.
   * <p>
   * The method can throw ServiceException extends RuntimeException
   */
  @Transactional
  @Override
  public void deleteById(Integer id) {
    if (!tagDAO.isExistById(id)) {
      throw new ServiceException(new ErrorDTO("tag.delete.error", id), 204);
    }
    tagDAO.deleteById(id);
  }
}
