package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.dao.entity.OrderEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.dao.page.PageDAO;
import com.epam.esm.dao.page.PageParamDAO;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.*;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.page.PageConvertorDTO;
import com.epam.esm.service.util.ServiceConvertor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserDAO userDAO;
  private final ServiceConvertor convertor;
  private final PageConvertorDTO convertorDTO;

  @Transactional
  @Override
  public PageDTO<UserDTO> findAll(PageParamDTO pageDTO) {
    PageDAO<UserEntity> pageDAO = userDAO.findAll(convertor.toTarget(pageDTO, PageParamDAO.class));
    return convertorDTO.toDto(pageDAO, UserDTO.class);
  }

  @Transactional
  @Override
  public PageDTO<UserDTO> findById(Integer id, PageParamDTO pageDTO) {
    if (!userDAO.isUserExist(id)) {
      throw new ServiceException(new ErrorDTO("user.search.error", id), 311);
    }
    PageDAO<UserEntity> pageDAO = userDAO.findById(id, convertor.toTarget(pageDTO, PageParamDAO.class));
    return convertorDTO.toDto(pageDAO, UserDTO.class);
  }

  @Transactional
  @Override
  public PageDTO<OrderDTO> findByIdOrders(Integer id, PageParamDTO pageDTO) {
    if (!userDAO.isUserExist(id)) {
      throw new ServiceException(new ErrorDTO("user.search.error", id), 322);
    }
    PageDAO<OrderEntity> pageDAO = userDAO.findByIdOrders(id, convertor.toTarget(pageDTO, PageParamDAO.class));
    if (pageDAO.getList().isEmpty()) {
      throw new ServiceException(new ErrorDTO("user.search.orders", id), 323);
    }
    return convertorDTO.toDto(pageDAO, OrderDTO.class);
  }

  @Transactional
  @Override
  public PageDTO<OrderDTO> findByIdOrder(Integer userId, Integer orderId, PageParamDTO pageDTO) {
    if (!userDAO.isUserExist(userId)) {
      throw new ServiceException(new ErrorDTO("user.search.error", userId), 334);
    }
    PageDAO<OrderEntity> pageDAO =
        userDAO.findByIdOrderById(userId, orderId, convertor.toTarget(pageDTO, PageParamDAO.class));
    if (pageDAO.getList().isEmpty()) {
      throw new ServiceException(new ErrorDTO("user.search.order", userId, orderId), 334);
    }
    return convertorDTO.toDto(pageDAO, OrderDTO.class);
  }

  @Override
  public PageDTO<TagDTO> findTagWithCost(Integer id, PageParamDTO pageDTO) {
    if (!userDAO.isUserExist(id)) {
      throw new ServiceException(new ErrorDTO("user.search.error", id), 341);
    }
    PageDAO<TagEntity> pageDAO = userDAO.findTagWithCost(id, convertor.toTarget(pageDTO, PageParamDAO.class));
    return convertorDTO.toDto(pageDAO, TagDTO.class);
  }
}
