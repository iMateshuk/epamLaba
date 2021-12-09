package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.dao.entity.OrderEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.dao.util.OrderSQL;
import com.epam.esm.dao.util.UserSQL;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@AllArgsConstructor
public class UserDaoImpl implements UserDAO {
  private final EntityManager entityManager;

  private final static String ID = "id";

  @Override
  public List<UserEntity> findAll(int pageNumber, int pageSize) {
    return entityManager.createQuery(UserSQL.SELECT_ALL.getSQL(), UserEntity.class)
        .setFirstResult(pageNumber * pageSize)
        .setMaxResults(pageSize)
        .getResultList();
  }

  @Override
  public long count() {
    return entityManager.createQuery(UserSQL.COUNT_ID.getSQL(), Long.class).getSingleResult();
  }

  @Override
  public UserEntity findById(Integer id) {
    return entityManager.find(UserEntity.class, id);
  }

  @Override
  public boolean isUserExist(Integer id) {
    return findById(id) != null;
  }

  @Override
  public List<OrderEntity> findOrdersByUserId(Integer userId, int pageNumber, int pageSize) {
    return entityManager.createQuery(OrderSQL.ORDERS_USER_ID.getSQL(), OrderEntity.class)
        .setParameter(ID, userId)
        .setFirstResult(pageNumber * pageSize)
        .setMaxResults(pageSize)
        .getResultList();
  }

  @Override
  public long count(Integer orderId) {
    return entityManager.createQuery(OrderSQL.ORDERS_COUNT_USER_ID.getSQL(), Long.class)
        .setParameter(ID, orderId)
        .getSingleResult();
  }

  @Override
  public OrderEntity findUserOrderById(Integer userId, Integer orderId) {
    final String UID = "uid";
    final String OID = "oid";
    return entityManager.createQuery(OrderSQL.ORDERS_ID_USER_ID.getSQL(), OrderEntity.class)
        .setParameter(UID, userId)
        .setParameter(OID, orderId)
        .getSingleResult();
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<TagEntity> findTagWithCost(Integer userId, int pageNumber, int pageSize) {
    return entityManager.createNativeQuery(UserSQL.SELECT_USED_TAGS.getSQL(), TagEntity.class)
        .setParameter(ID, userId)
        .setFirstResult(pageNumber * pageSize)
        .setMaxResults(pageSize)
        .getResultList();
  }

  @Override
  public long countNativeQuery(Integer id) {
    return ((Number) entityManager.createNativeQuery(UserSQL.COUNT_USED_TAGS.getSQL())
        .setParameter(ID, id)
        .getSingleResult()
    ).longValue();
  }
}
