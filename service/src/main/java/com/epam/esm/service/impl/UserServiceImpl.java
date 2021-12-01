package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.*;
import com.epam.esm.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserDAO userDAO;
  private final UserConverter userConverter;
  private final OrderConvertor orderConvertor;
  private final TagConverter tagConverter;

  @Transactional
  @Override
  public List<UserDTO> findAll() {
    return userConverter.toDto(userDAO.findAll());
  }

  @Transactional
  @Override
  public UserDTO findById(Integer id) {
    if(!userDAO.isUserExist(id)){
      throw new ServiceException(new ErrorDto("user.search.error", id), 311);
    }
    return userConverter.toDto(userDAO.findById(id));
  }

  @Transactional
  @Override
  public List<OrderDTO> findByIdOrders(Integer id) {
    if(!userDAO.isUserExist(id)){
      throw new ServiceException(new ErrorDto("user.search.error", id), 322);
    }
    List<OrderDTO> orders = orderConvertor.toDto(userDAO.findByIdOrders(id));
    if(orders.isEmpty()){
      throw new ServiceException(new ErrorDto("user.search.orders", id), 323);
    }
    return orders;
  }

  @Transactional
  @Override
  public OrderDTO findByIdOrder(Integer userId, Integer orderId) {
    if(!userDAO.isUserExist(userId)){
      throw new ServiceException(new ErrorDto("user.search.error", userId), 334);
    }
    OrderDTO orderDTO = orderConvertor.toDto(userDAO.findByIdOrderById(userId, orderId));
    if(orderDTO == null){
      throw new ServiceException(new ErrorDto("user.search.order", userId, orderId), 334);
    }
    return orderDTO;
  }

  @Override
  public List<TagDTO> findTagWithCost(Integer id) {
    if(!userDAO.isUserExist(id)){
      throw new ServiceException(new ErrorDto("user.search.error", id), 341);
    }
    return tagConverter.toDto(userDAO.findTagWithCost(id));
  }
}
