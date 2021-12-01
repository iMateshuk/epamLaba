package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.entity.OrderEntity;
import com.epam.esm.dao.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@AllArgsConstructor
public class OrderDAOImpl implements OrderDAO {
  private final EntityManager entityManager;

  @Override
  public OrderEntity insert(OrderEntity orderEntity) {
    UserEntity parentUser = orderEntity.getUser();
    GiftCertificateEntity parentCert = orderEntity.getCertificate();
    parentUser.getOrders().add(orderEntity);
    parentCert.getOrders().add(orderEntity);
    orderEntity.setUser(parentUser);
    orderEntity.setCertificate(parentCert);
    entityManager.persist(orderEntity);
    return findById(orderEntity.getId());
  }

  @Override
  public OrderEntity findById(Integer id) {
    return entityManager.find(OrderEntity.class, id);
  }
}
