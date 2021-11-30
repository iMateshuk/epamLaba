package com.epam.esm.controller;

import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.OrderDTO;
import com.epam.esm.service.dto.UserDTO;
import com.epam.esm.service.util.Validator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;
  private final Validator validator;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<UserDTO> findAll(){
    return userService.findAll();
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public UserDTO findById(@PathVariable int id){
    validator.checkId(id);
    return userService.findById(id);
  }

  @GetMapping("/{id}/orders")
  @ResponseStatus(HttpStatus.OK)
  public List<OrderDTO> findByIdOrders(@PathVariable int id){
    validator.checkId(id);
    return userService.findByIdOrders(id);
  }

  @GetMapping("/{userId}/orders/{orderId}")
  @ResponseStatus(HttpStatus.OK)
  public OrderDTO findByIdOrder(@PathVariable int userId, @PathVariable int orderId){
    validator.checkId(userId);
    validator.checkId(orderId);
    return userService.findByIdOrder(userId, orderId);
  }
}
