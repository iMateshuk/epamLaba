package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.UserConverter;
import com.epam.esm.service.dto.UserDTO;
import com.epam.esm.service.util.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserDAO userDAO;
  private final UserConverter userConverter;
  private final Validator validator;

  @Override
  public List<UserDTO> findAll() {
    return userConverter.toDto(userDAO.findAll());
  }
}
