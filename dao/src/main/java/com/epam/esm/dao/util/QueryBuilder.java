package com.epam.esm.dao.util;

import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.entity.TagEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@AllArgsConstructor
@Component
public class QueryBuilder {
  private final EntityManager entityManager;

  private static final String LIKE = "%";
  private static final String SEARCH_REG_EX = "^SEARCH_.*";
  private static final String SORT_REG_EX = "^SORT_.*";
  private static final String JOIN_REG_EX = "^JOIN.*";
  private static final String TAGS_ATTRIBUTE_NAME = "tags";
  private static final String NAME = "name";
  private static final String ID = "id";
  private static final String SPLIT = ",";

  public CriteriaQuery<GiftCertificateEntity> buildQuery(Map<String, String> params) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<GiftCertificateEntity> query = builder.createQuery(GiftCertificateEntity.class);
    Root<GiftCertificateEntity> root = query.from(GiftCertificateEntity.class);

    List<Predicate> predicates = createPredicates(root, builder, params, query);
    if (!predicates.isEmpty()) {
      query.where(predicates.toArray(new Predicate[0]));
    }
    query.groupBy(root.get(ID));

    List<Order> orders = new ArrayList<>();
    Arrays.stream(PredicateParameter.class.getEnumConstants())
        .filter(item -> item.toString().matches(SORT_REG_EX) && params.get(item.toString()) != null)
        .forEach(item -> {
          if (params.get(item.toString()).equalsIgnoreCase(PredicateParameter.ORDER_DESC.getSQL())) {
            orders.add(builder.desc(root.get(item.getSQL())));
          }
          if (params.get(item.toString()).equalsIgnoreCase(PredicateParameter.ORDER_ASC.getSQL())) {
            orders.add(builder.asc(root.get(item.getSQL())));
          }
        });
    if (!orders.isEmpty()) {
      query.orderBy(orders);
    }
    return query;
  }

  public CriteriaQuery<Long> buildCountQuery(Map<String, String> params) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Long> query = builder.createQuery(Long.class);
    Root<GiftCertificateEntity> root = query.from(GiftCertificateEntity.class);
    Subquery<GiftCertificateEntity> subQuery = query.subquery(GiftCertificateEntity.class);
    Root<GiftCertificateEntity> root_ = subQuery.from(GiftCertificateEntity.class);
    subQuery.select(root_);

    List<Predicate> predicates = createPredicates(root_, builder, params, subQuery);
    if (!predicates.isEmpty()) {
      subQuery.where(predicates.toArray(new Predicate[0]));
    }
    subQuery.groupBy(root_.get(ID));
    query.select(builder.count(root)).where(builder.in(root).value(subQuery));
    return query;
  }

  private List<Predicate> createPredicates(Root<GiftCertificateEntity> root, CriteriaBuilder builder,
                                           Map<String, String> params, AbstractQuery<GiftCertificateEntity> query) {
    List<Predicate> predicates = new ArrayList<>();

    Arrays.stream(PredicateParameter.values())
        .filter(param -> param.toString().matches(SEARCH_REG_EX) && params.get(param.toString()) != null)
        .forEach(param -> predicates.add(
            builder.like(root.get(param.getSQL()), LIKE + params.get(param.toString()) + LIKE))
        );

    Join<GiftCertificateEntity, TagEntity> join = root.join(TAGS_ATTRIBUTE_NAME, JoinType.LEFT);
    Stream.of(PredicateParameter.values())
        .filter(item -> item.toString().matches(JOIN_REG_EX) && params.get(item.toString()) != null)
        .forEach(item -> {
          List<String> tagNames = List.of(params.get(item.toString()).split(SPLIT));
          predicates.add(join.get(NAME).in(tagNames));
          query.having(builder.count(root).in(tagNames.size()));
        });
    return predicates;
  }
}
