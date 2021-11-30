package com.epam.esm.dao.util;

import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.entity.TagEntity;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@AllArgsConstructor
public class QueryCreator {
  private final EntityManager entityManager;

  public CriteriaQuery<GiftCertificateEntity> buildWithParameters(Map<String, String> parameters) {
    final String SEARCH_LIKE = "%";
    final String SEARCH = "^SEARCH_.*";
    final String SORT = "^SORT_.*";
    final String JOIN = "^JOIN.*";
    final String TAGS_ATTRIBUTE_NAME = "tags";
    final String NAME = "name";
    final String ID = "id";
    final String SPLIT = ",";

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<GiftCertificateEntity> criteriaQuery = criteriaBuilder.createQuery(GiftCertificateEntity.class);
    Root<GiftCertificateEntity> certRoot = criteriaQuery.from(GiftCertificateEntity.class);

    List<Predicate> predicates = new ArrayList<>();

    Arrays.stream(PredicateParameter.values())
        .filter(param -> param.toString().matches(SEARCH) && parameters.get(param.toString()) != null)
        .forEach(param -> predicates.add(criteriaBuilder.like(certRoot.get(param.getSQL()),
            SEARCH_LIKE + parameters.get(param.toString()) + SEARCH_LIKE)));

    Join<GiftCertificateEntity, TagEntity> join = certRoot.join(TAGS_ATTRIBUTE_NAME, JoinType.LEFT);
    Stream.of(PredicateParameter.values())
        .filter(item -> item.toString().matches(JOIN) && parameters.get(item.toString()) != null)
        .forEach(item -> {
          String[] tagNames = parameters.get(item.toString()).split(SPLIT);
          predicates.add(join.get(NAME).in(tagNames));
          criteriaQuery.having(criteriaBuilder.count(certRoot).in(tagNames.length));
        });
    if (!predicates.isEmpty()) {
      criteriaQuery.where(predicates.toArray(new Predicate[0]));
    }
    criteriaQuery.groupBy(certRoot.get(ID));

    List<Order> orders = new ArrayList<>();
    Arrays.stream(PredicateParameter.class.getEnumConstants())
        .filter(item -> item.toString().matches(SORT) && parameters.get(item.toString()) != null)
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
}
