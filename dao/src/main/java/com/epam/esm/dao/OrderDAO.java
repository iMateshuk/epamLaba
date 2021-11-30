package com.epam.esm.dao;

import com.epam.esm.dao.entity.OrderEntity;

public interface OrderDAO {

  OrderEntity insert(OrderEntity orderEntity);

  OrderEntity findById(Integer id);
}
