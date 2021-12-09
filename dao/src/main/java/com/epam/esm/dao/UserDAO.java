package com.epam.esm.dao;

import com.epam.esm.dao.entity.OrderEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.entity.UserEntity;

import java.util.List;

public interface UserDAO {

  /**
   *
   * @param pageNumber
   * @param pageSize
   * @return lost of userEntities
   */
  List<UserEntity> findAll(int pageNumber, int pageSize);

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
   * @param userId
   * @param pageNumber
   * @param pageSize
   * @return list of user orders
   */
  List<OrderEntity> findOrdersByUserId(Integer userId, int pageNumber, int pageSize);

  /**
   *
   * @param orderId
   * @return count of all user orders records
   */
  long count(Integer orderId);

  /**
   *
   * @param userId
   * @param pageNumber
   * @param pageSize
   * @return list of all user's orders tags with high cost and most uses
   */
  List<TagEntity> findTagWithCost(Integer userId, int pageNumber, int pageSize);

  /**
   *
   * @param id
   * @return count of user's orders tags with high cost and most uses
   */
  long countNativeQuery(Integer id);
}
