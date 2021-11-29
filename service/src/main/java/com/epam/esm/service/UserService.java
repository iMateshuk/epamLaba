package com.epam.esm.service;

import com.epam.esm.service.dto.OrderDTO;
import com.epam.esm.service.dto.UserDTO;

import java.util.List;

public interface UserService {

  List<UserDTO> findAll();
  UserDTO findById(Integer id);
  List<OrderDTO> findByIdOrders(Integer id);
  OrderDTO findByIdOrder(Integer userId, Integer orderId);
}
