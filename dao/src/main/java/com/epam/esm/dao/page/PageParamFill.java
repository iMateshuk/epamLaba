package com.epam.esm.dao.page;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
@AllArgsConstructor
public class PageParamFill {
  private final EntityManager entityManager;

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
    String result = String.valueOf(
        entityManager.createNativeQuery(sql).setParameter("id", id).getSingleResult());
    long count;
    try {
      count = Long.parseLong(result);
    } catch (NumberFormatException | NullPointerException ignore) {
      count = 1L;
    }
    return fillingPage(pageParamDAO, count);
  }
}
