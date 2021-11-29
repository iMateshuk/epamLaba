package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.dao.util.UserSQL;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@AllArgsConstructor
public class UserDaoImpl implements UserDAO {
  private final EntityManager entityManager;

  @Override
  public List<UserEntity> findAll() {
    return entityManager.createQuery(UserSQL.QL_SELECT_ALL.getSQL(), UserEntity.class)
        /*.setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)*/
        .getResultList();
  }
}
