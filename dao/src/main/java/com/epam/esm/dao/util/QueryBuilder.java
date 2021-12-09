package com.epam.esm.dao.util;

import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.entity.TagEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

  private final static String MATCH_WHERE_REG_EX = ".*WHERE.*";
  private final static String WHERE = "WHERE ";
  private final static String AND = "AND ";
  private final static String REPLACE_REG_EX = "\\?";
  private final static String SPLIT_REG_EX = ",";
  private final static String EMPTY = "";
  private final static String QOUTE = "'";

  private final String START = "SELECT count(*) FROM (";
  private final String END = ") as agg";

  public CriteriaQuery<GiftCertificateEntity> buildQuery(Map<String, String> params) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<GiftCertificateEntity> query = builder.createQuery(GiftCertificateEntity.class);
    Root<GiftCertificateEntity> parentRoot = query.from(GiftCertificateEntity.class);

    List<Predicate> predicates = new ArrayList<>();
    Arrays.stream(PredicateParameter.values())
        .filter(param -> param.toString().matches(SEARCH_REG_EX) && params.get(param.toString()) != null)
        .forEach(param -> predicates.add(
            builder.like(parentRoot.get(param.getSQL()), LIKE + params.get(param.toString()) + LIKE))
        );

    Join<GiftCertificateEntity, TagEntity> join = parentRoot.join(TAGS_ATTRIBUTE_NAME, JoinType.LEFT);
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

  public String buildNativeQuery(String sql, Map<String, String> params) {
    StringBuilder sqlBuilder = new StringBuilder(START);
    StringBuilder searchBuilder = new StringBuilder();

    params.entrySet().stream().filter((param) -> param.getKey().matches(SEARCH_REG_EX))
        .forEach((param) -> searchBuilder.append(appendSearch(searchBuilder, param.getKey(), param.getValue())));
    params.entrySet().stream().filter((param) -> param.getKey().matches(JOIN_REG_EX))
        .forEach((param) -> searchBuilder.append(appendSearch(searchBuilder, param.getKey(), param.getValue())));
    return sqlBuilder
        .append(sql)
        .append(searchBuilder)
        .append(GiftCertificateSQL.APPEND_GROUP.getSQL())
        .append(END)
        .toString();
  }

  private String appendSearch(StringBuilder builder, String key, String value) {
    String match = builder.toString().matches(MATCH_WHERE_REG_EX) ? AND : WHERE;

    List<String> values =
        Arrays.stream(value.split(SPLIT_REG_EX)).map(val -> QOUTE + val + QOUTE).collect(Collectors.toList());
    StringBuilder addValue = new StringBuilder();
    values.forEach(val -> addValue.append(val).append(SPLIT_REG_EX));
    addValue.replace(addValue.length() - 1, addValue.length(), EMPTY);

    return (match + GiftCertificateSQL.valueOf(key).getSQL()).replaceAll(REPLACE_REG_EX, addValue.toString());
  }
}
