package com.epam.esm.dao;

import com.epam.esm.dao.entity.OrderEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.dao.util.PageDAO;
import com.epam.esm.dao.util.PageEntity;

import java.util.List;

public interface UserDAO {

  PageDAO<UserEntity> findAll(PageEntity pageDAO);
  UserEntity findById(Integer id);
  boolean isUserExist(Integer id);
  List<OrderEntity> findByIdOrders(Integer id);
  OrderEntity findByIdOrderById(Integer userId, Integer orderId);
  List<TagEntity> findTagWithCost(Integer id);
}
