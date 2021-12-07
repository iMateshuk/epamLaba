package com.epam.esm.dao;

import com.epam.esm.dao.entity.OrderEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.dao.page.PageData;

import java.util.List;

public interface UserDAO {

  List<UserEntity> findAll(PageData pageDAO);

  long count();

  UserEntity findById(Integer id);

  boolean isUserExist(Integer id);

  List<OrderEntity> findByIdOrders(Integer orderID, PageData pageData);

  long count(Integer orderId);

  OrderEntity findByIdOrderById(Integer userId, Integer orderId);

  List<TagEntity> findTagWithCost(Integer id, PageData pageData);

  long countNativeQuery(Integer id);
}
