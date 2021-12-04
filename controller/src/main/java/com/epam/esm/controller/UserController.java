package com.epam.esm.controller;

import com.epam.esm.hateoas.*;
import com.epam.esm.page.PageModelCreator;
import com.epam.esm.page.PageParamCreator;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.PageDTO;
import com.epam.esm.service.dto.PageParamDTO;
import com.epam.esm.service.dto.UserDTO;
import com.epam.esm.service.util.Validator;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;
  private final Validator validator;
  private final UserAssembler userAssembler;
  private final OrderAssembler orderAssembler;
  private final TagAssembler tagAssembler;
  private final PageParamCreator pageParamCreator;
  private final PageModelCreator modelCreator;

  @GetMapping
  public ResponseEntity<?> findAll(@RequestParam Map<String, String> parameters) {
    PageParamDTO pageDTO = pageParamCreator.buildPageDTO(parameters);
    PageDTO<UserDTO> pageService = userService.findAll(pageDTO);
    return new ResponseEntity<>(modelCreator.createModel(pageService, userAssembler), HttpStatus.OK);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserModel> findById(@PathVariable int userId) {
    validator.checkId(userId);
    UserModel userModel = userAssembler.toModel(userService.findById(userId));
    userModel.add(linkTo(methodOn(UserController.class).findById(userId)).withSelfRel());
    return new ResponseEntity<>(userModel, HttpStatus.OK);
  }

  @GetMapping("/{userId}/orders")
  public ResponseEntity<CollectionModel<OrderModel>> findByIdOrders(@PathVariable int userId) {
    validator.checkId(userId);
    List<OrderModel> orders = orderAssembler.toModel(userService.findByIdOrders(userId));
    return new ResponseEntity<>(
        CollectionModel.of(orders, linkTo(methodOn(UserController.class).findByIdOrders(userId)).withSelfRel()),
        HttpStatus.OK
    );
  }

  @GetMapping("/{userId}/orders/{orderId}")
  public ResponseEntity<OrderModel> findByIdOrder(@PathVariable int userId, @PathVariable int orderId) {
    validator.checkId(userId);
    validator.checkId(orderId);
    OrderModel orderModel = orderAssembler.toModel(userService.findByIdOrder(userId, orderId));
    orderModel.add(linkTo(methodOn(UserController.class).findByIdOrder(userId, orderId)).withSelfRel());
    return new ResponseEntity<>(orderModel, HttpStatus.OK);
  }

  @GetMapping("/{id}/orders/tags")
  public ResponseEntity<CollectionModel<TagModel>> findMostUsedTagWithCost(@PathVariable int id) {
    validator.checkId(id);
    List<TagModel> tags = tagAssembler.toModel(userService.findTagWithCost(id));
    return new ResponseEntity<>(
        CollectionModel.of(tags, linkTo(methodOn(UserController.class).findMostUsedTagWithCost(id)).withSelfRel()),
        HttpStatus.OK
    );
  }
}
