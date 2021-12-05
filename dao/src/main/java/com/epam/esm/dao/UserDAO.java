package com.epam.esm.dao;

import com.epam.esm.dao.entity.OrderEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.dao.page.PageDAO;
import com.epam.esm.dao.page.PageParamDAO;

public interface UserDAO {

  PageDAO<UserEntity> findAll(PageParamDAO pageDAO);
  PageDAO<UserEntity> findById(Integer id, PageParamDAO pageParamDAO);
  UserEntity findById(Integer id);
  boolean isUserExist(Integer id);
  PageDAO<OrderEntity> findByIdOrders(Integer id, PageParamDAO pageParamDAO);
  PageDAO<OrderEntity> findByIdOrderById(Integer userId, Integer orderId, PageParamDAO pageParamDAO);
  PageDAO<TagEntity> findTagWithCost(Integer id, PageParamDAO pageParamDAO);
}
