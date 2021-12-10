package com.epam.esm.service.impl;

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
  private final Mapper mapper;

  @Override
  public Page<UserDTO> findAll(PageParam pageParam) {
    int pageNumber = pageParam.getPageNumber();
    int pageSize = pageParam.getPageSize();

    List<UserEntity> users = userDAO.findAll(pageNumber, pageSize);
    long count = userDAO.count();

    return Page.<UserDTO>builder()
        .pageSize(pageSize)
        .pageNumber(pageNumber)
        .totalElements(count)
        .lastPage(count / pageParam.getPageSize())
        .list(mapper.toTarget(users, UserDTO.class))
        .build();
  }

  @Override
  public UserDTO findById(Integer id) {
    UserEntity userEntity = userDAO.findById(id);
    if (userEntity == null) {
      throw new ServiceException(new ErrorDTO("user.search.error", id), 311);
    }
    return mapper.toTarget(userEntity, UserDTO.class);
  }

  @Override
  public Page<OrderDTO> findOrdersByUserId(Integer userId, PageParam pageParam) {
    if (!userDAO.isUserExist(userId)) {
      throw new ServiceException(new ErrorDTO("user.search.error", userId), 322);
    }
    int pageNumber = pageParam.getPageNumber();
    int pageSize = pageParam.getPageSize();

    List<OrderEntity> orders = userDAO.findOrdersByUserId(userId, pageNumber, pageSize);
    long count = userDAO.count(userId);

    return Page.<OrderDTO>builder()
        .pageSize(pageSize)
        .pageNumber(pageNumber)
        .totalElements(count)
        .lastPage(count / pageParam.getPageSize())
        .list(mapper.toTarget(orders, OrderDTO.class))
        .build();
  }

  @Override
  public OrderDTO findUserOrderById(Integer userId, Integer orderId) {
    if (!userDAO.isUserExist(userId)) {
      throw new ServiceException(new ErrorDTO("user.search.error", userId), 334);
    }
    return mapper.toTarget(userDAO.findUserOrderById(userId, orderId), OrderDTO.class);
  }

  @Override
  public Page<TagDTO> findTagWithCost(Integer userId, PageParam pageParam) {
    if (!userDAO.isUserExist(userId)) {
      throw new ServiceException(new ErrorDTO("user.search.error", userId), 341);
    }
    int pageNumber = pageParam.getPageNumber();
    int pageSize = pageParam.getPageSize();

    List<TagEntity> tags = userDAO.findTagWithCost(userId, pageNumber, pageSize);
    long count = userDAO.countNativeQuery(userId);
    return Page.<TagDTO>builder()
        .pageSize(pageSize)
        .pageNumber(pageNumber)
        .totalElements(count)
        .lastPage(count / pageParam.getPageSize())
        .list(mapper.toTarget(tags, TagDTO.class))
        .build();
  }
}
