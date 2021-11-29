package com.epam.esm.controller;

import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.UserDTO;
import com.epam.esm.service.util.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;
  private final Validator validator;

  public UserController(UserService userService, Validator validator) {
    this.userService = userService;
    this.validator = validator;
  }

  @GetMapping
  public List<UserDTO> findAll(){
    return userService.findAll();
  }
}
