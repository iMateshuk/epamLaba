package com.epam.esm.service;

import com.epam.esm.service.dto.UserDTO;

import java.util.List;

public interface UserService {

  List<UserDTO> findAll();
}
