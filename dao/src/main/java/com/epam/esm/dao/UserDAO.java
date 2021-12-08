package com.epam.esm.dao;

import com.epam.esm.dao.entity.OrderEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.dao.page.PageData;

import java.util.List;

public interface UserDAO {

  /**
   *
   * @param pageDAO
   * @return lost of userEntities
   */
  List<UserEntity> findAll(PageData pageDAO);

  /**
   *
   * @return count of all userEntity records
   */
  long count();

  /**
   *
   * @param id
   * @return
   */
  UserEntity findById(Integer id);

  /**
   *
   * @param id
   * @return userEntity found by id
   */
  boolean isUserExist(Integer id);

  /**
   *
   * @param orderID
   * @param pageData
   * @return list of user orders
   */
  List<OrderEntity> findByIdOrders(Integer orderID, PageData pageData);

  /**
   *
   * @param orderId
   * @return count of all user orders records
   */
  long count(Integer orderId);

  /**
   *
   * @param userId
   * @param orderId
   * @return user's order found by order id
   */
  OrderEntity findByIdOrderById(Integer userId, Integer orderId);

  /**
   *
   * @param id
   * @param pageData
   * @return list of all user's orders tags with high cost and most uses
   */
  List<TagEntity> findTagWithCost(Integer id, PageData pageData);

  /**
   *
   * @param id
   * @return count of user's orders tags with high cost and most uses
   */
  long countNativeQuery(Integer id);
}
