package com.epam.esm.dao.util;

import com.epam.esm.dao.page.PageParamDAO;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@AllArgsConstructor
public class QueryWork {
  private final EntityManager entityManager;

  private static final String LIKE = "%";
  private static final String SEARCH_REG_EX = "^SEARCH_.*";
  private static final String SORT_REG_EX = "^SORT_.*";
  private static final String JOIN_REG_EX = "^JOIN.*";
  private static final String TAGS_ATTRIBUTE_NAME = "tags";
  private static final String NAME = "name";
  private static final String ID = "id";
  private static final String SPLIT = ",";

  public <P, C> CriteriaQuery<P> buildQuery(Map<String, String> params, Class<P> parent, Class<C> child) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<P> query = builder.createQuery(parent);
    Root<P> parentRoot = query.from(parent);

    List<Predicate> predicates = new ArrayList<>();
    Arrays.stream(PredicateParameter.values())
        .filter(param -> param.toString().matches(SEARCH_REG_EX) && params.get(param.toString()) != null)
        .forEach(param -> predicates.add(
            builder.like(parentRoot.get(param.getSQL()), LIKE + params.get(param.toString()) + LIKE))
        );

    Join<P, C> join = parentRoot.join(TAGS_ATTRIBUTE_NAME, JoinType.LEFT);
    Stream.of(PredicateParameter.values())
        .filter(item -> item.toString().matches(JOIN_REG_EX) && params.get(item.toString()) != null)
        .forEach(item -> {
          List<String> tagNames = List.of(params.get(item.toString()).split(SPLIT));
          predicates.add(join.get(NAME).in(tagNames));
          query.having(builder.count(parentRoot).in(tagNames.size()));
        });

    if (!predicates.isEmpty()) {
      query.where(predicates.toArray(new Predicate[0]));
    }
    query.groupBy(parentRoot.get(ID));

    List<Order> orders = new ArrayList<>();
    Arrays.stream(PredicateParameter.class.getEnumConstants())
        .filter(item -> item.toString().matches(SORT_REG_EX) && params.get(item.toString()) != null)
        .forEach(item -> {
          if (params.get(item.toString()).equalsIgnoreCase(PredicateParameter.ORDER_DESC.getSQL())) {
            orders.add(builder.desc(parentRoot.get(item.getSQL())));
          }
          if (params.get(item.toString()).equalsIgnoreCase(PredicateParameter.ORDER_ASC.getSQL())) {
            orders.add(builder.asc(parentRoot.get(item.getSQL())));
          }
        });
    if (!orders.isEmpty()) {
      query.orderBy(orders);
    }

    return query;
  }


  public <T> List<T> executeNativeQuery(PageParamDAO pageDAO, String sql, Class<T> type, Integer id) {
    int pageNumber = pageDAO.getNumber();
    int pageSize = pageDAO.getSize();
    return entityManager.createNativeQuery(sql, type)
        .setParameter("id", id)
        .setFirstResult(pageNumber * pageSize)
        .setMaxResults(pageSize)
        .getResultList();
  }

  public <T> List<T> executeQuery(PageParamDAO pageDAO, String sql, Class<T> type) {
    int pageNumber = pageDAO.getNumber();
    int pageSize = pageDAO.getSize();
    return entityManager.createQuery(sql, type)
        .setFirstResult(pageNumber * pageSize)
        .setMaxResults(pageSize)
        .getResultList();
  }

  public <T> List<T> executeQuery(PageParamDAO pageDAO, String sql, Class<T> type, Integer id) {
    int pageNumber = pageDAO.getNumber();
    int pageSize = pageDAO.getSize();
    return entityManager.createQuery(sql, type)
        .setParameter("id", id)
        .setFirstResult(pageNumber * pageSize)
        .setMaxResults(pageSize)
        .getResultList();
  }

  public <T> List<T> executeQuery(PageParamDAO pageDAO, String sql, Class<T> type, Integer firstId, Integer secondId) {
    int pageNumber = pageDAO.getNumber();
    int pageSize = pageDAO.getSize();
    return entityManager.createQuery(sql, type)
        .setParameter("fid", firstId)
        .setParameter("sid", secondId)
        .setFirstResult(pageNumber * pageSize)
        .setMaxResults(pageSize)
        .getResultList();
  }

  public <T> List<T> executeQuery(PageParamDAO pageDAO, CriteriaQuery<T> query) {
    int pageNumber = pageDAO.getNumber();
    int pageSize = pageDAO.getSize();
    return entityManager.createQuery(query)
        .setFirstResult(pageNumber * pageSize)
        .setMaxResults(pageSize)
        .getResultList();
  }
}
