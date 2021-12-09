package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.ErrorDTO;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.exception.ServiceConflictException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.page.Page;
import com.epam.esm.service.page.PageParam;
import com.epam.esm.service.util.Mapper;
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
  private final Mapper mapper;

  @Transactional
  @Override
  public TagDTO insertByName(String name) {
    validator.matchField(name);
    if (tagDAO.isExistByName(name)) {
      throw new ServiceConflictException(new ErrorDTO("tag.create.error", name), 201);
    }
    return mapper.toTarget(tagDAO.insertByName(name), TagDTO.class);
  }

  @Transactional
  @Override
  public Page<TagDTO> findAll(PageParam pageParam) {
    int pageNumber = pageParam.getPageNumber();
    int pageSize = pageParam.getPageSize();

    List<TagEntity> tags= tagDAO.findAll(pageNumber, pageSize);
    Long count = tagDAO.count();

    return Page.<TagDTO>builder()
        .pageSize(pageSize)
        .pageNumber(pageNumber)
        .totalElements(count)
        .totalPages(count / pageParam.getPageSize())
        .list(mapper.toTarget(tags, TagDTO.class))
        .build();
  }

  @Transactional
  @Override
  public TagDTO findById(Integer id) {
    if (!tagDAO.isExistById(id)) {
      throw new ServiceException(new ErrorDTO("tag.search.error", id), 203);
    }
    return mapper.toTarget(tagDAO.findById(id), TagDTO.class);
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
