package com.epam.esm.dao;

import com.epam.esm.dao.entity.UserEntity;

import java.util.List;

public interface UserDAO {

  List<UserEntity> findAll();
}
