package com.epam.esm.service;

import com.epam.esm.service.dto.AuthRequest;
import com.epam.esm.service.dto.AuthResponse;
import com.epam.esm.service.dto.OrderDTO;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.dto.UserDTO;
import com.epam.esm.service.page.Page;
import com.epam.esm.service.page.PageParam;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

  /**
   *
   * @param authRequest
   * @return
   */
  UserDTO save(AuthRequest authRequest);

  /**
   *
   * @param authRequest
   * @return
   */
  AuthResponse login(AuthRequest authRequest);

  /**
   *
   * @param pageDTO
   * @return page, include list of userDTOs
   */
  Page<UserDTO> findAll(PageParam pageDTO);

  /**
   *
   * @param id
   * @return userDTO found by id
   */
  UserDTO findById(Integer id);

  /**
   *
   * @param login
   * @return
   */
  UserDetails findByLogin(String login);

  /**
   *
   * @param userId
   * @param pageParam
   * @return page, include list of orderDTOs
   */
  Page<OrderDTO> findOrdersByUserId(Integer userId, PageParam pageParam);

  /**
   *
   * @param orderId
   * @return user order found by userId and orderId
   */
  OrderDTO findUserOrderById(Integer userId, Integer orderId);

  /**
   *
   * @param userId
   * @param pageParam
   * @return page, include list of tagDTOs
   */
  Page<TagDTO> findTagWithCost(Integer userId, PageParam pageParam);
}
