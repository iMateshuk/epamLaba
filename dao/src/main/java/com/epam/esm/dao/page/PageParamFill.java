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
    pageParamDAO.setTotalElements(count);
    pageParamDAO.setTotalPages(count / pageParamDAO.getSize());
    return pageParamDAO;
  }

  public PageParamDAO fillingPage(PageParamDAO pageParamDAO, String sql, Integer id) {
    Long count = entityManager.createQuery(sql, Long.class).setParameter("id", id).getSingleResult();
    pageParamDAO.setTotalElements(count);
    pageParamDAO.setTotalPages(count / pageParamDAO.getSize());
    return pageParamDAO;
  }
}
