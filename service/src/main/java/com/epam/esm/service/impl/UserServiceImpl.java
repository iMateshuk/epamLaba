package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.dao.entity.OrderEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.dao.page.PageData;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.ErrorDTO;
import com.epam.esm.service.dto.OrderDTO;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.dto.UserDTO;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.page.Page;
import com.epam.esm.service.page.PageParam;
import com.epam.esm.service.util.ServiceConvertor;
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
  public Page<UserDTO> findAll(PageParam pageParam) {
    List<UserEntity> users = userDAO.findAll(convertor.toTarget(pageParam, PageData.class));
    long count = userDAO.count();
    return Page.<UserDTO>builder()
        .size(pageParam.getSize())
        .number(pageParam.getNumber())
        .totalElements(count)
        .totalPages(count / pageParam.getSize())
        .list(convertor.toTarget(users, UserDTO.class))
        .build();
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
  public Page<OrderDTO> findByIdOrders(Integer id, PageParam pageParam) {
    if (!userDAO.isUserExist(id)) {
      throw new ServiceException(new ErrorDTO("user.search.error", id), 322);
    }
    List<OrderEntity> orders = userDAO.findByIdOrders(id, convertor.toTarget(pageParam, PageData.class));
    if (orders.isEmpty()) {
      throw new ServiceException(new ErrorDTO("user.search.orders", id), 323);
    }
    long count = userDAO.count(id);
    return Page.<OrderDTO>builder()
        .size(pageParam.getSize())
        .number(pageParam.getNumber())
        .totalElements(count)
        .totalPages(count / pageParam.getSize())
        .list(convertor.toTarget(orders, OrderDTO.class))
        .build();
  }

  @Transactional
  @Override
  public OrderDTO findByIdOrder(Integer userId, Integer orderId) {
    if (!userDAO.isUserExist(userId)) {
      throw new ServiceException(new ErrorDTO("user.search.error", userId), 334);
    }
    return convertor.toTarget(userDAO.findByIdOrderById(userId, orderId), OrderDTO.class);
  }

  @Override
  public Page<TagDTO> findTagWithCost(Integer id, PageParam pageParam) {
    if (!userDAO.isUserExist(id)) {
      throw new ServiceException(new ErrorDTO("user.search.error", id), 341);
    }
    List<TagEntity> tags = userDAO.findTagWithCost(id, convertor.toTarget(pageParam, PageData.class));
    long count = userDAO.countNativeQuery(id);
    return Page.<TagDTO>builder()
        .size(pageParam.getSize())
        .number(pageParam.getNumber())
        .totalElements(count)
        .totalPages(count / pageParam.getSize())
        .list(convertor.toTarget(tags, TagDTO.class))
        .build();
  }
}
