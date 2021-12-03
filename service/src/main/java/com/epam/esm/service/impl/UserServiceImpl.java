package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.dao.util.PageDAO;
import com.epam.esm.dao.util.PageEntity;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.*;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.util.ServiceConvertor;
import com.epam.esm.service.util.PageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserDAO userDAO;
  private final ServiceConvertor convertor;

  @Transactional
  @Override
  public PageService<UserDTO> findAll(PageDTO pageDTO) {
    PageDAO<UserEntity> pageDAO = userDAO.findAll(convertor.toTarget(pageDTO, PageEntity.class));
    PageService<UserDTO> pageService = new PageService<>();
    pageService.setPage(convertor.toTarget(pageDAO.getPage(), PageDTO.class));
    pageService.setList(convertor.toTarget(pageDAO.getList(), UserDTO.class));
    return pageService;
  }

  @Transactional
  @Override
  public UserDTO findById(Integer id) {
    if (!userDAO.isUserExist(id)) {
      throw new ServiceException(new ErrorDTO("user.search.error", id), 311);
    }
    return convertor.toTarget(userDAO.findById(id), UserDTO.class);
  }

  @Transactional
  @Override
  public List<OrderDTO> findByIdOrders(Integer id) {
    if (!userDAO.isUserExist(id)) {
      throw new ServiceException(new ErrorDTO("user.search.error", id), 322);
    }
    List<OrderDTO> orders = convertor.toTarget(userDAO.findByIdOrders(id), OrderDTO.class);
    if (orders.isEmpty()) {
      throw new ServiceException(new ErrorDTO("user.search.orders", id), 323);
    }
    return orders;
  }

  @Transactional
  @Override
  public OrderDTO findByIdOrder(Integer userId, Integer orderId) {
    if (!userDAO.isUserExist(userId)) {
      throw new ServiceException(new ErrorDTO("user.search.error", userId), 334);
    }
    OrderDTO orderDTO = convertor.toTarget(userDAO.findByIdOrderById(userId, orderId), OrderDTO.class);
    if (orderDTO == null) {
      throw new ServiceException(new ErrorDTO("user.search.order", userId, orderId), 334);
    }
    return orderDTO;
  }

  @Override
  public List<TagDTO> findTagWithCost(Integer id) {
    if (!userDAO.isUserExist(id)) {
      throw new ServiceException(new ErrorDTO("user.search.error", id), 341);
    }
    return convertor.toTarget(userDAO.findTagWithCost(id), TagDTO.class);
  }
}
