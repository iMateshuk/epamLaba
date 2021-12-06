package com.epam.esm.service;

import com.epam.esm.service.dto.*;

public interface UserService {

  PageDTO<UserDTO> findAll(PageParamDTO pageDTO);

  PageDTO<UserDTO> findById(Integer id, PageParamDTO pageParamDTO);

  PageDTO<OrderDTO> findByIdOrders(Integer id, PageParamDTO pageParamDTO);

  PageDTO<OrderDTO> findByIdOrder(Integer userId, Integer orderId, PageParamDTO pageParamDTO);

  PageDTO<TagDTO> findTagWithCost(Integer id, PageParamDTO pageParamDTO);
}
