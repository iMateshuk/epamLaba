package com.epam.esm.dao;

import com.epam.esm.dao.entity.OrderEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.dao.page.Page;
import com.epam.esm.dao.page.PageParam;

public interface UserDAO {

  Page<UserEntity> findAll(PageParam pageDAO);

  Page<UserEntity> findById(Integer id, PageParam pageParam);

  UserEntity findById(Integer id);

  boolean isUserExist(Integer id);

  Page<OrderEntity> findByIdOrders(Integer id, PageParam pageParam);

  Page<OrderEntity> findByIdOrderById(Integer userId, Integer orderId, PageParam pageParam);

  Page<TagEntity> findTagWithCost(Integer id, PageParam pageParam);
}
