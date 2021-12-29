package com.epam.esm.dao;

import com.epam.esm.dao.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDAO extends JpaRepository<RoleEntity, Integer> {

  RoleEntity findByName(String name);
}
