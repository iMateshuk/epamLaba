package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.dao.entity.OrderEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.dao.page.PageDAO;
import com.epam.esm.dao.page.PageParamDAO;
import com.epam.esm.dao.page.PageParamFill;
import com.epam.esm.dao.util.QueryWork;
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
  private final QueryWork queryWork;
  private final PageParamFill pageFill;

  @Override
  public PageDAO<UserEntity> findAll(PageParamDAO pageParamDAO) {
    List<UserEntity> users = queryWork.executeQuery(pageParamDAO, UserSQL.SELECT_ALL.getSQL(), UserEntity.class);
    pageFill.fillingPage(pageParamDAO, UserSQL.COUNT_ID.getSQL());
    return new PageDAO<>(users, pageParamDAO);
  }

  @Override
  public PageDAO<UserEntity> findById(Integer id, PageParamDAO pageParamDAO) {
    List<UserEntity> users = List.of(entityManager.find(UserEntity.class, id));
    pageFill.fillingPage(pageParamDAO, UserSQL.COUNT_ID.getSQL(), id);
    return new PageDAO<>(users, pageParamDAO);
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
