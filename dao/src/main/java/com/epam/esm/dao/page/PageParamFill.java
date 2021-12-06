package com.epam.esm.dao.page;

import com.epam.esm.dao.util.GiftCertificateSQL;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PageParamFill {
  private final EntityManager entityManager;

  private static final String SEARCH_REG_EX = "^SEARCH_.*";
  private static final String JOIN_REG_EX = "^JOIN.*";
  private final static String MATCH_WHERE_REG_EX = ".*WHERE.*";
  private final static String WHERE = "WHERE ";
  private final static String AND = "AND ";
  private final static String REPLACE_REG_EX = "\\?";
  private final static String SPLIT_REG_EX = ",";
  private final static String EMPTY = "";
  private final static String QOUTE = "'";

  private final String START = "SELECT count(*) FROM (";
  private final String END = ") as agg";

  public PageParamDAO fillingPage(PageParamDAO pageParamDAO, String sql) {
    Long count = entityManager.createQuery(sql, Long.class).getSingleResult();
    return fillingPage(pageParamDAO, count);
  }

  public PageParamDAO fillingPage(PageParamDAO pageParamDAO, String sql, Integer id) {
    Long count = entityManager.createQuery(sql, Long.class).setParameter("id", id).getSingleResult();
    return fillingPage(pageParamDAO, count);
  }

  public PageParamDAO fillingPage(PageParamDAO pageParamDAO, Long count) {
    pageParamDAO.setTotalElements(count);
    pageParamDAO.setTotalPages(count / pageParamDAO.getSize());
    return pageParamDAO;
  }

  public PageParamDAO fillingPageNativeQuery(PageParamDAO pageParamDAO, String sql, Integer id) {
    return fillingPage(pageParamDAO,
        checkQueryResult(entityManager.createNativeQuery(sql).setParameter("id", id).getSingleResult()));
  }

  public PageParamDAO fillingPageNativeQuery(PageParamDAO pageParamDAO, String sql, Map<String, String> params) {
    StringBuilder sqlBuilder = new StringBuilder(START);
    StringBuilder searchBuilder = new StringBuilder();

    params.entrySet().stream().filter((param) -> param.getKey().matches(SEARCH_REG_EX))
        .forEach((param) -> searchBuilder.append(appendSearch(searchBuilder, param.getKey(), param.getValue())));
    params.entrySet().stream().filter((param) -> param.getKey().matches(JOIN_REG_EX))
        .forEach((param) -> searchBuilder.append(appendSearch(searchBuilder, param.getKey(), param.getValue())));
    final String query = sqlBuilder
        .append(sql)
        .append(searchBuilder)
        .append(GiftCertificateSQL.APPEND_GROUP.getSQL())
        .append(END)
        .toString();
    return fillingPage(pageParamDAO, checkQueryResult(entityManager.createNativeQuery(query).getSingleResult()));
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
