package com.epam.esm.controller;

import com.epam.esm.hateoas.OrderAssembler;
import com.epam.esm.hateoas.TagAssembler;
import com.epam.esm.hateoas.UserAssembler;
import com.epam.esm.page.PageModelCreator;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.OrderDTO;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.dto.UserDTO;
import com.epam.esm.service.page.Page;
import com.epam.esm.service.page.PageParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;
  private final UserAssembler userAssembler;
  private final OrderAssembler orderAssembler;
  private final TagAssembler tagAssembler;
  private final PageModelCreator modelCreator;

  @GetMapping
  public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") @Min(0) @Max(Integer.MAX_VALUE) int number,
                                   @RequestParam(required = false, defaultValue = "20") @Min(2) @Max(50) int size) {
    PageParam pageParam = PageParam.builder().number(number).size(size).build();
    Page<UserDTO> page = userService.findAll(pageParam);
    return new ResponseEntity<>(
        modelCreator.createModel(page, userAssembler, linkTo(UserController.class)),
        HttpStatus.OK);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<?> findById(@PathVariable @Min(1) @Max(Integer.MAX_VALUE) int userId) {
    return new ResponseEntity<>(userAssembler.toModel(userService.findById(userId)), HttpStatus.OK);
  }

  @GetMapping("/{userId}/orders")
  public ResponseEntity<?> findByIdOrders(@PathVariable @Min(1) @Max(Integer.MAX_VALUE) int userId,
                                          @RequestParam(required = false, defaultValue = "0") @Min(0) @Max(Integer.MAX_VALUE) int number,
                                          @RequestParam(required = false, defaultValue = "20") @Min(2) @Max(50) int size) {
    PageParam pageParam = PageParam.builder().number(number).size(size).build();
    Page<OrderDTO> page = userService.findByIdOrders(userId, pageParam);
    linkTo(UserController.class).slash(userId).slash("orders");
    return new ResponseEntity<>(
        modelCreator.createModel(page, orderAssembler, linkTo(UserController.class).slash(userId).slash("orders")),
        HttpStatus.OK);
  }

  @GetMapping("/{userId}/orders/{orderId}")
  public ResponseEntity<?> findByIdOrder(@PathVariable @Min(1) @Max(Integer.MAX_VALUE) int userId,
                                         @PathVariable @Min(1) @Max(Integer.MAX_VALUE) int orderId) {
    return new ResponseEntity<>(orderAssembler.toModel(userService.findByIdOrder(userId, orderId)), HttpStatus.OK);
  }

  @GetMapping("/{id}/orders/tags")
  public ResponseEntity<?> findMostUsedTagWithCost(@PathVariable @Min(1) @Max(Integer.MAX_VALUE) int id,
                                                   @RequestParam(required = false, defaultValue = "0") @Min(0) @Max(Integer.MAX_VALUE) int number,
                                                   @RequestParam(required = false, defaultValue = "20") @Min(2) @Max(50) int size) {
    PageParam pageParam = PageParam.builder().number(number).size(size).build();
    Page<TagDTO> page = userService.findTagWithCost(id, pageParam);
    return new ResponseEntity<>(
        modelCreator.createModel(page, tagAssembler, linkTo(UserController.class).slash(id).slash("orders").slash("tags")),
        HttpStatus.OK);
  }
}
