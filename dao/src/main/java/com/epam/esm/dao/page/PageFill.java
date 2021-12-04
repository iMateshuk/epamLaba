package com.epam.esm.dao.page;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
@AllArgsConstructor
public class PageFill {
  private final EntityManager entityManager;
  public PageParamDAO fillingPage(PageParamDAO pageParamDAO, String sql) {
    Long count = entityManager.createQuery(sql, Long.class).getSingleResult();
    pageParamDAO.setTotalElements(count);
    pageParamDAO.setTotalPages(count / pageParamDAO.getSize());
    return pageParamDAO;
  }
}
