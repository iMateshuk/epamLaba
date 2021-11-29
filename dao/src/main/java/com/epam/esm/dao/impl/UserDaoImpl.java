package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.dao.entity.OrderEntity;
import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.dao.util.UserSQL;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

@Repository
@AllArgsConstructor
public class UserDaoImpl implements UserDAO {
  private final EntityManager entityManager;

  @Override
  public List<UserEntity> findAll() {
    return entityManager.createQuery(UserSQL.SELECT_ALL.getSQL(), UserEntity.class).getResultList();
  }

  @Override
  public UserEntity findById(Integer id) {
    return entityManager.find(UserEntity.class, id);
  }

  @Override
  public boolean isUserExist(Integer id) {
    return entityManager.contains(findById(id));
  }

  @Override
  public List<OrderEntity> findByIdOrders(Integer id) {
    return findById(id).getOrders();
  }

  @Override
  public OrderEntity findByIdOrderById(Integer userId, Integer orderId) {
    return findById(userId).getOrders().stream()
        .filter(order -> Objects.equals(order.getId(), orderId))
        .findFirst().orElse(null);
  }
}
