package com.epam.esm.controller;

import com.epam.esm.hateoas.OrderAssembler;
import com.epam.esm.hateoas.TagAssembler;
import com.epam.esm.hateoas.UserAssembler;
import com.epam.esm.page.PageModelCreator;
import com.epam.esm.page.PageModelLink;
import com.epam.esm.page.PageParamCreator;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.*;
import com.epam.esm.service.util.ServiceConvertor;
import com.epam.esm.service.util.Validator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


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
  private final PageModelLink modelLink;
  private final ServiceConvertor convertor;

  @GetMapping
  public ResponseEntity<?> findAll(@RequestParam Map<String, String> parameters) {
    PageParamDTO pageParamDTO = pageParamCreator.buildPageDTOAndRemoveKey(parameters);
    PageDTO<UserDTO> page = userService.findAll(pageParamDTO);
    return new ResponseEntity<>(
        modelCreator.createModel(page, userAssembler, linkTo(UserController.class)),
        HttpStatus.OK);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<?> findById(@PathVariable int userId, @RequestParam Map<String, String> parameters) {
    validator.checkId(userId);
    PageParamDTO pageParamDTO = pageParamCreator.buildPageDTOAndRemoveKey(parameters);
    PageDTO<UserDTO> page = userService.findById(userId, pageParamDTO);
    return new ResponseEntity<>(
        modelCreator.createModel(page, userAssembler, linkTo(UserController.class).slash(userId)),
        HttpStatus.OK);
  }

  @GetMapping("/{userId}/orders")
  public ResponseEntity<?> findByIdOrders(@PathVariable int userId,
                                          @RequestParam Map<String, String> parameters) {
    validator.checkId(userId);
    PageParamDTO pageParamDTO = pageParamCreator.buildPageDTOAndRemoveKey(parameters);
    PageDTO<OrderDTO> page = userService.findByIdOrders(userId, pageParamDTO);
    linkTo(UserController.class).slash(userId).slash("orders");
    return new ResponseEntity<>(
        modelCreator.createModel(page, orderAssembler, linkTo(UserController.class).slash(userId).slash("orders")),
        HttpStatus.OK);
  }

  @GetMapping("/{userId}/orders/{orderId}")
  public ResponseEntity<?> findByIdOrder(@PathVariable int userId, @PathVariable int orderId,
                                         @RequestParam Map<String, String> parameters) {
    validator.checkId(userId);
    validator.checkId(orderId);
    PageParamDTO pageParamDTO = pageParamCreator.buildPageDTOAndRemoveKey(parameters);
    PageDTO<OrderDTO> page = userService.findByIdOrder(userId, orderId, pageParamDTO);
    return new ResponseEntity<>(
        modelCreator.createModel(page, orderAssembler,
            linkTo(UserController.class).slash(userId).slash("orders").slash("orderId")),
        HttpStatus.OK);
  }

  @GetMapping("/{id}/orders/tags")
  public ResponseEntity<?> findMostUsedTagWithCost(@PathVariable int id,
                                                                           @RequestParam Map<String, String> parameters) {
    validator.checkId(id);
    PageParamDTO pageParamDTO = pageParamCreator.buildPageDTOAndRemoveKey(parameters);
    PageDTO<TagDTO> page = userService.findTagWithCost(id, pageParamDTO);
    return new ResponseEntity<>(
        modelCreator.createModel(page, tagAssembler, linkTo(UserController.class).slash(id).slash("orders").slash("tags")),
        HttpStatus.OK);
  }
}
