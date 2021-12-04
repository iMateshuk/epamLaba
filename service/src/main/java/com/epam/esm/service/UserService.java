package com.epam.esm.service;

import com.epam.esm.service.dto.OrderDTO;
import com.epam.esm.service.dto.PageParamDTO;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.dto.UserDTO;
import com.epam.esm.service.dto.PageDTO;

import java.util.List;

public interface UserService {

  PageDTO<UserDTO> findAll(PageParamDTO pageDTO);
  PageDTO<UserDTO> findById(Integer id, PageParamDTO pageParamDTO);
  List<OrderDTO> findByIdOrders(Integer id);
  OrderDTO findByIdOrder(Integer userId, Integer orderId);
  List<TagDTO> findTagWithCost(Integer id);
}
