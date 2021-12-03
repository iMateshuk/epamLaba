package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.dao.entity.OrderEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.dao.util.PageEntity;
import com.epam.esm.dao.util.PageDAO;
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
  public PageDAO<UserEntity> findAll(PageEntity pageEntity) {
    int pageNumber = pageEntity.getNumber();
    int pageSize = pageEntity.getSize();
    List<UserEntity> users = entityManager.createQuery(UserSQL.SELECT_ALL.getSQL(), UserEntity.class)
        .setFirstResult(pageNumber * pageSize)
        .setMaxResults(pageSize)
        .getResultList();

    Long count = entityManager.createQuery(UserSQL.COUNT_ALL.getSQL(), Long.class).getSingleResult();
    pageEntity.setTotalElements(count);
    pageEntity.setTotalPages(count / pageSize);

    return new PageDAO<UserEntity>(users, pageEntity);
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

  @Override
  public List<TagEntity> findTagWithCost(Integer id) {
    return entityManager.createNativeQuery(UserSQL.SELECT_USED_TAGS.getSQL(), TagEntity.class)
        .setParameter("id", id).getResultList();
  }
}
