package com.epam.esm.dao;

import com.epam.esm.dao.entity.OrderEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.entity.UserEntity;

import java.util.List;

public interface UserDAO {

  /**
   * @param userEntity
   * @return userEntity
   */
  UserEntity save(UserEntity userEntity);

  /**
   * @param pageNumber
   * @param pageSize
   * @return list of userEntities
   */
  List<UserEntity> findAll(int pageNumber, int pageSize);

  /**
   * @return count of all userEntity records
   */
  long count();

  /**
   * @param id
   * @return userEntity
   */
  UserEntity findById(Integer id);

  /**
   * @param login
   * @return userEntity
   */
  UserEntity findByLogin(String login);

  /**
   * @param login
   * @return true if found user by login
   */
  boolean isUserExist(String login);

  /**
   * @param id
   * @return true if found user by id
   */
  boolean isUserExist(Integer id);

  /**
   * @param userId
   * @param pageNumber
   * @param pageSize
   * @return list of user orders
   */
  List<OrderEntity> findOrdersByUserId(Integer userId, int pageNumber, int pageSize);

  /**
   * @param orderId
   * @return count of all user orders records
   */
  long count(Integer orderId);

  /**
   * @param userId
   * @param orderId
   * @return user's order found by order id
   */
  OrderEntity findUserOrderById(Integer userId, Integer orderId);

  /**
   * @param userId
   * @param pageNumber
   * @param pageSize
   * @return list of all user's orders tags with high cost and most uses
   */
  List<TagEntity> findTagWithCost(Integer userId, int pageNumber, int pageSize);

  /**
   * @param id
   * @return count of user's orders tags with high cost and most uses
   */
  long countNativeQuery(Integer id);
}
