package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dao.entity.OrderEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.ErrorDTO;
import com.epam.esm.service.dto.OrderDTO;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.dto.UserDTO;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.page.Page;
import com.epam.esm.service.page.PageParam;
import com.epam.esm.service.util.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service User
 * Use for business logic of APP
 *
 * @author Ivan Matsiashuk
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserDAO userDAO;
  private final OrderDAO orderDAO;
  private final Mapper mapper;

  @Transactional
  @Override
  public Page<UserDTO> findAll(PageParam pageParam) {
    List<UserEntity> users = userDAO.findAll(pageParam.getPageNumber(), pageParam.getPageSize());
    long count = userDAO.count();
    return Page.<UserDTO>builder()
        .size(pageParam.getPageSize())
        .number(pageParam.getPageNumber())
        .totalElements(count)
        .totalPages(count / pageParam.getPageSize())
        .list(mapper.toTarget(users, UserDTO.class))
        .build();
  }

  @Transactional
  @Override
  public UserDTO findById(Integer id) {
    if (!userDAO.isUserExist(id)) {
      throw new ServiceException(new ErrorDTO("user.search.error", id), 311);
    }
    return mapper.toTarget(userDAO.findById(id), UserDTO.class);
  }

  @Transactional
  @Override
  public Page<OrderDTO> findOrdersByUserId(Integer userId, PageParam pageParam) {
    if (!userDAO.isUserExist(userId)) {
      throw new ServiceException(new ErrorDTO("user.search.error", userId), 322);
    }
    List<OrderEntity> orders = userDAO.findOrdersByUserId(userId, pageParam.getPageNumber(), pageParam.getPageSize());
    long count = userDAO.count(userId);
    return Page.<OrderDTO>builder()
        .size(pageParam.getPageSize())
        .number(pageParam.getPageNumber())
        .totalElements(count)
        .totalPages(count / pageParam.getPageSize())
        .list(mapper.toTarget(orders, OrderDTO.class))
        .build();
  }

  @Transactional
  @Override
  public OrderDTO findOrderById(Integer orderId) {
    return mapper.toTarget(orderDAO.findById(orderId), OrderDTO.class);
  }

  @Override
  public Page<TagDTO> findTagWithCost(Integer userId, PageParam pageParam) {
    if (!userDAO.isUserExist(userId)) {
      throw new ServiceException(new ErrorDTO("user.search.error", userId), 341);
    }
    List<TagEntity> tags = userDAO.findTagWithCost(userId, pageParam.getPageNumber(), pageParam.getPageSize());
    long count = userDAO.countNativeQuery(userId);
    return Page.<TagDTO>builder()
        .size(pageParam.getPageSize())
        .number(pageParam.getPageNumber())
        .totalElements(count)
        .totalPages(count / pageParam.getPageSize())
        .list(mapper.toTarget(tags, TagDTO.class))
        .build();
  }
}
