package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.page.PageData;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.ErrorDTO;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.exception.ServiceConflictException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.page.Page;
import com.epam.esm.service.page.PageParam;
import com.epam.esm.service.util.ServiceConvertor;
import com.epam.esm.service.util.Validator;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Service
public class TagServiceImpl implements TagService {
  private final TagDAO tagDAO;
  private final Validator validator;
  private final ServiceConvertor convertor;

  @Transactional
  @Override
  public TagDTO insertByName(String name) {
    validator.matchField(name);
    if (tagDAO.isExistByName(name)) {
      throw new ServiceConflictException(new ErrorDTO("tag.create.error", name), 201);
    }
    return convertor.toTarget(tagDAO.insertByName(name), TagDTO.class);
  }

  @Transactional
  @Override
  public Page<TagDTO> findAll(PageParam pageParam) {
    List<TagEntity> tags= tagDAO.findAll(convertor.toTarget(pageParam, PageData.class));
    Long count = tagDAO.count();

    return Page.<TagDTO>builder()
        .size(pageParam.getSize())
        .number(pageParam.getNumber())
        .totalElements(count)
        .totalPages(count / pageParam.getSize())
        .list(convertor.toTarget(tags, TagDTO.class))
        .build();
  }

  @Transactional
  @Override
  public TagDTO findById(Integer id) {
    if (!tagDAO.isExistById(id)) {
      throw new ServiceException(new ErrorDTO("tag.search.error", id), 203);
    }
    return convertor.toTarget(tagDAO.findById(id), TagDTO.class);
  }

  @Transactional
  @Override
  public void deleteById(Integer id) {
    if (!tagDAO.isExistById(id)) {
      throw new ServiceException(new ErrorDTO("tag.delete.error", id), 204);
    }
    tagDAO.deleteById(id);
  }
}
