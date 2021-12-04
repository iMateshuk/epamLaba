package com.epam.esm.dao;

import com.epam.esm.dao.entity.OrderEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.dao.page.PageDAO;
import com.epam.esm.dao.page.PageParamDAO;

import java.util.List;

public interface UserDAO {

  PageDAO<UserEntity> findAll(PageParamDAO pageDAO);
  UserEntity findById(Integer id);
  boolean isUserExist(Integer id);
  List<OrderEntity> findByIdOrders(Integer id);
  OrderEntity findByIdOrderById(Integer userId, Integer orderId);
  List<TagEntity> findTagWithCost(Integer id);
}
