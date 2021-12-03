package com.epam.esm.service;

import com.epam.esm.service.dto.OrderDTO;
import com.epam.esm.service.dto.PageDTO;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.dto.UserDTO;
import com.epam.esm.service.util.PageService;

import java.util.List;

public interface UserService {

  PageService<UserDTO> findAll(PageDTO pageDTO);
  UserDTO findById(Integer id);
  List<OrderDTO> findByIdOrders(Integer id);
  OrderDTO findByIdOrder(Integer userId, Integer orderId);
  List<TagDTO> findTagWithCost(Integer id);
}
