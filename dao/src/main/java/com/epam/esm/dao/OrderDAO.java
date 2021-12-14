package com.epam.esm.dao;

import com.epam.esm.dao.entity.OrderEntity;

public interface OrderDAO {

  /**
   *
   * @param orderEntity
   * @return new orderEntity
   */
  OrderEntity insert(OrderEntity orderEntity);

  /**
   *
   * @param id
   * @return orderEntity found by id
   */
  OrderEntity findById(Integer id);
}
