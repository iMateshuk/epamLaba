package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.dao.entity.OrderEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.dao.page.Page;
import com.epam.esm.dao.page.PageParam;
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
  public Page<UserEntity> findAll(PageParam pageParam) {
    int pageNumber = pageParam.getNumber();
    int pageSize = pageParam.getSize();
    List<UserEntity> users = entityManager.createQuery(UserSQL.SELECT_ALL.getSQL(), UserEntity.class)
        .setFirstResult(pageNumber * pageSize)
        .setMaxResults(pageSize)
        .getResultList();
    Long count = entityManager.createQuery(UserSQL.COUNT_ID.getSQL(), Long.class).getSingleResult();
    fillPage(pageParam, count);
    return new Page<>(users, pageParam);
  }

  @Override
  public Page<UserEntity> findById(Integer id, PageParam pageParam) {
    List<UserEntity> users = List.of(entityManager.find(UserEntity.class, id));
    Long count = entityManager.createQuery(UserSQL.COUNT_ID.getSQL(), Long.class)
        .setParameter(ID, id)
        .getSingleResult();
    fillPage(pageParam, count);
    return new Page<>(users, pageParam);
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
  public Page<OrderEntity> findByIdOrders(Integer id, PageParam pageParam) {
    int pageNumber = pageParam.getNumber();
    int pageSize = pageParam.getSize();
    List<OrderEntity> orders = entityManager.createQuery(OrderSQL.ORDERS_USER_ID.getSQL(), OrderEntity.class)
        .setParameter(ID, id)
        .setFirstResult(pageNumber * pageSize)
        .setMaxResults(pageSize)
        .getResultList();
    Long count = entityManager.createQuery(OrderSQL.ORDERS_COUNT_USER_ID.getSQL(), Long.class)
        .setParameter(ID, id)
        .getSingleResult();
    fillPage(pageParam, count);
    return new Page<>(orders, pageParam);
  }

  @Override
  public Page<OrderEntity> findByIdOrderById(Integer userId, Integer orderId, PageParam pageParam) {
    final String UID = "uid";
    final String OID = "oid";
    int pageNumber = pageParam.getNumber();
    int pageSize = pageParam.getSize();
    List<OrderEntity> orders = entityManager.createQuery(OrderSQL.ORDERS_ID_USER_ID.getSQL(), OrderEntity.class)
        .setParameter(UID, userId)
        .setParameter(OID, orderId)
        .setFirstResult(pageNumber * pageSize)
        .setMaxResults(pageSize)
        .getResultList();
    Long count = entityManager.createQuery(OrderSQL.COUNT_ID.getSQL(), Long.class)
        .setParameter(ID, orderId)
        .getSingleResult();
    fillPage(pageParam, count);
    return new Page<>(orders, pageParam);
  }

  @Override
  public Page<TagEntity> findTagWithCost(Integer id, PageParam pageParam) {
    int pageNumber = pageParam.getNumber();
    int pageSize = pageParam.getSize();
    List<TagEntity> entities = entityManager.createNativeQuery(UserSQL.SELECT_USED_TAGS.getSQL(), TagEntity.class)
        .setParameter(ID, id)
        .setFirstResult(pageNumber * pageSize)
        .setMaxResults(pageSize)
        .getResultList();
    Long count = checkQueryResult(entityManager.createNativeQuery(UserSQL.COUNT_USED_TAGS.getSQL())
        .setParameter(ID, id)
        .getSingleResult());
    fillPage(pageParam, count);
    return new Page<>(entities, pageParam);
  }

  private void fillPage(PageParam pageParam, Long count) {
    pageParam.setTotalElements(count);
    pageParam.setTotalPages(count / pageParam.getSize());
  }

  private Long checkQueryResult(Object result) {
    long count;
    try {
      count = Long.parseLong(String.valueOf(result));
    } catch (NumberFormatException | NullPointerException ignore) {
      count = 1L;
    }
    return count;
  }
}
