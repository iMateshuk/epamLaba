package com.epam.esm.service;

import com.epam.esm.service.dto.OrderDTO;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.dto.UserDTO;
import com.epam.esm.service.page.Page;
import com.epam.esm.service.page.PageParam;

public interface UserService {

  Page<UserDTO> findAll(PageParam pageDTO);

  UserDTO findById(Integer id);

  Page<OrderDTO> findByIdOrders(Integer id, PageParam pageParam);

  OrderDTO findByIdOrder(Integer userId, Integer orderId);

  Page<TagDTO> findTagWithCost(Integer id, PageParam pageParam);
}
