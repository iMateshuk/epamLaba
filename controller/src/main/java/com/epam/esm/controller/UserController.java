package com.epam.esm.controller;

import com.epam.esm.service.security.Role;
import com.epam.esm.hateoas.OrderAssembler;
import com.epam.esm.hateoas.OrderModel;
import com.epam.esm.hateoas.PageModel;
import com.epam.esm.hateoas.TagAssembler;
import com.epam.esm.hateoas.TagModel;
import com.epam.esm.hateoas.UserAssembler;
import com.epam.esm.hateoas.UserModel;
import com.epam.esm.page.PageModelCreator;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.OrderDTO;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.dto.UserDTO;
import com.epam.esm.service.page.Page;
import com.epam.esm.service.page.PageParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/users")
public class UserController {
  private final UserService userService;
  private final UserAssembler userAssembler;
  private final OrderAssembler orderAssembler;
  private final TagAssembler tagAssembler;
  private final PageModelCreator modelCreator;

  @RolesAllowed(Role.ROLE_ADMIN)
  @GetMapping
  public ResponseEntity<PageModel<UserModel>> findAll(
      @RequestParam(required = false, defaultValue = "0") @Min(0) @Max(Integer.MAX_VALUE) int pageNumber,
      @RequestParam(required = false, defaultValue = "20") @Min(2) @Max(50) int pageSize) {

    PageParam pageParam = PageParam.builder().pageNumber(pageNumber).pageSize(pageSize).build();
    Page<UserDTO> page = userService.findAll(pageParam);

    return ResponseEntity.ok(modelCreator.createModel(page, userAssembler, linkTo(UserController.class)));
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserModel> findById(@PathVariable @Min(1) @Max(Integer.MAX_VALUE) int userId) {
    return ResponseEntity.ok(userAssembler.toModel(userService.findById(userId)));
  }

  @GetMapping("/{userId}/orders")
  public ResponseEntity<PageModel<OrderModel>> findOrdersByUserId(
      @PathVariable @Min(1) @Max(Integer.MAX_VALUE) int userId,
      @RequestParam(required = false, defaultValue = "0") @Min(0) @Max(Integer.MAX_VALUE) int pageNumber,
      @RequestParam(required = false, defaultValue = "20") @Min(2) @Max(50) int pageSize) {

    PageParam pageParam = PageParam.builder().pageNumber(pageNumber).pageSize(pageSize).build();
    Page<OrderDTO> page = userService.findOrdersByUserId(userId, pageParam);
    linkTo(UserController.class).slash(userId).slash("orders");
    return ResponseEntity.ok(
        modelCreator.createModel(page, orderAssembler, linkTo(UserController.class).slash(userId).slash("orders"))
    );
  }

  @GetMapping("/{userId}/orders/{orderId}")
  public ResponseEntity<OrderModel> findUserOrderById(@PathVariable @Min(1) @Max(Integer.MAX_VALUE) int userId,
                                                      @PathVariable @Min(1) @Max(Integer.MAX_VALUE) int orderId) {
    return ResponseEntity.ok(orderAssembler.toModel(userService.findUserOrderById(userId, orderId)));
  }

  @GetMapping("/{userId}/orders/tags")
  public ResponseEntity<PageModel<TagModel>> findMostUsedTagWithCost(
      @PathVariable @Min(1) @Max(Integer.MAX_VALUE) int userId,
      @RequestParam(required = false, defaultValue = "0") @Min(0) @Max(Integer.MAX_VALUE) int pageNumber,
      @RequestParam(required = false, defaultValue = "20") @Min(2) @Max(50) int pageSize) {

    PageParam pageParam = PageParam.builder().pageNumber(pageNumber).pageSize(pageSize).build();
    Page<TagDTO> page = userService.findTagWithCost(userId, pageParam);
    return ResponseEntity.ok(
        modelCreator.createModel(page, tagAssembler, linkTo(UserController.class)
            .slash(userId)
            .slash("orders")
            .slash("tags"))
    );
  }
}
