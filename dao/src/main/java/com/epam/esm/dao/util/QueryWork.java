package com.epam.esm.dao.util;

import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.entity.TagEntity;
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

  public CriteriaQuery<GiftCertificateEntity> buildQuery(Map<String, String> parameters) {
    final String SEARCH_LIKE = "%";
    final String SEARCH_REG_EX = "^SEARCH_.*";
    final String SORT_REG_EX = "^SORT_.*";
    final String JOIN_REG_EX = "^JOIN.*";
    final String TAGS_ATTRIBUTE_NAME = "tags";
    final String NAME = "name";
    final String ID = "id";
    final String SPLIT = ",";

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<GiftCertificateEntity> criteriaQuery = criteriaBuilder.createQuery(GiftCertificateEntity.class);
    Root<GiftCertificateEntity> certRoot = criteriaQuery.from(GiftCertificateEntity.class);

    List<Predicate> predicates = new ArrayList<>();

    Arrays.stream(PredicateParameter.values())
        .filter(param -> param.toString().matches(SEARCH_REG_EX) && parameters.get(param.toString()) != null)
        .forEach(param -> predicates.add(criteriaBuilder.like(certRoot.get(param.getSQL()),
            SEARCH_LIKE + parameters.get(param.toString()) + SEARCH_LIKE)));

    Join<GiftCertificateEntity, TagEntity> join = certRoot.join(TAGS_ATTRIBUTE_NAME, JoinType.LEFT);
    Stream.of(PredicateParameter.values())
        .filter(item -> item.toString().matches(JOIN_REG_EX) && parameters.get(item.toString()) != null)
        .forEach(item -> {
          List<String> tagNames = List.of(parameters.get(item.toString()).split(SPLIT));
          predicates.add(join.get(NAME).in(tagNames));
          criteriaQuery.having(criteriaBuilder.count(certRoot).in(tagNames.size()));
        });
    if (!predicates.isEmpty()) {
      criteriaQuery.where(predicates.toArray(new Predicate[0]));
    }
    criteriaQuery.groupBy(certRoot.get(ID));

    List<Order> orders = new ArrayList<>();
    Arrays.stream(PredicateParameter.class.getEnumConstants())
        .filter(item -> item.toString().matches(SORT_REG_EX) && parameters.get(item.toString()) != null)
        .forEach(item -> {
          if (parameters.get(item.toString()).equalsIgnoreCase(PredicateParameter.ORDER_DESC.getSQL())) {
            orders.add(criteriaBuilder.desc(certRoot.get(item.getSQL())));
          }
          if (parameters.get(item.toString()).equalsIgnoreCase(PredicateParameter.ORDER_ASC.getSQL())) {
            orders.add(criteriaBuilder.asc(certRoot.get(item.getSQL())));
          }
        });
    if (!orders.isEmpty()) {
      criteriaQuery.orderBy(orders);
    }
    return criteriaQuery;
  }

  public <T> List<T> executeQuery(PageParamDAO pageParamDAO, String sql, Class<T> type) {
    int pageNumber = pageParamDAO.getNumber();
    int pageSize = pageParamDAO.getSize();
    return entityManager.createQuery(sql, type)
        .setFirstResult(pageNumber * pageSize)
        .setMaxResults(pageSize)
        .getResultList();
  }

  public <T> List<T> executeQuery(PageParamDAO pageParamDAO, CriteriaQuery<T> query) {
    int pageNumber = pageParamDAO.getNumber();
    int pageSize = pageParamDAO.getSize();
    return entityManager.createQuery(query)
        .setFirstResult(pageNumber * pageSize)
        .setMaxResults(pageSize)
        .getResultList();
  }
}
