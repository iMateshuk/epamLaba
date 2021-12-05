package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.dao.entity.OrderEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.dao.page.PageDAO;
import com.epam.esm.dao.page.PageParamDAO;
import com.epam.esm.dao.page.PageParamFill;
import com.epam.esm.dao.util.OrderSQL;
import com.epam.esm.dao.util.QueryWork;
import com.epam.esm.dao.util.UserSQL;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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
  public PageDAO<OrderEntity> findByIdOrders(Integer id, PageParamDAO pageParamDAO) {
    List<OrderEntity> orders =
        queryWork.executeQuery(pageParamDAO, OrderSQL.ORDERS_USER_ID.getSQL(), OrderEntity.class, id);
    pageFill.fillingPage(pageParamDAO, OrderSQL.ORDERS_COUNT_USER_ID.getSQL(), id);
    return new PageDAO<>(orders, pageParamDAO);
  }

  @Override
  public PageDAO<OrderEntity> findByIdOrderById(Integer userId, Integer orderId, PageParamDAO pageParamDAO) {
    List<OrderEntity> orders =
        queryWork.executeQuery(pageParamDAO, OrderSQL.ORDERS_ID_USER_ID.getSQL(), OrderEntity.class, userId, orderId);
    pageFill.fillingPage(pageParamDAO, OrderSQL.COUNT_ID.getSQL(), orderId);
    return new PageDAO<>(orders, pageParamDAO);
  }

  @Override
  public PageDAO<TagEntity> findTagWithCost(Integer id, PageParamDAO pageParamDAO) {
    List<TagEntity> entities =
        queryWork.executeNativeQuery(pageParamDAO, UserSQL.SELECT_USED_TAGS.getSQL(), TagEntity.class, id);

    String result = String.valueOf(
        entityManager.createNativeQuery(UserSQL.COUNT_USED_TAGS.getSQL()).setParameter("id", id).getSingleResult());
    long count;
    try {
      count = Long.parseLong(result);
    } catch (NumberFormatException | NullPointerException ignore) {
      count = 1L;
    }
    pageFill.fillingPage(pageParamDAO, count);
    return new PageDAO<>(entities, pageParamDAO);
  }
}
