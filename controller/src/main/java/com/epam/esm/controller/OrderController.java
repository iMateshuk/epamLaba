package com.epam.esm.controller;

import com.epam.esm.hateoas.OrderAssembler;
import com.epam.esm.hateoas.OrderModel;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.dto.PurchaseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/orders")
public class OrderController {
  private final OrderService orderService;
  private final OrderAssembler orderAssembler;

  @GetMapping("/{id}")
  public ResponseEntity<OrderModel> findById(@PathVariable @Min(1) @Max(Integer.MAX_VALUE) int id) {
    OrderModel orderModel = orderAssembler.toModel(orderService.findById(id));
    return new ResponseEntity<>(orderModel, HttpStatus.OK);
  }

  @PreAuthorize("@guard.checkUserId(authentication,#purchaseDTO.userId) or @guard.checkUserRole(authentication)")
  @PostMapping
  public ResponseEntity<OrderModel> insert(@Validated @RequestBody PurchaseDTO purchaseDTO) {
    OrderModel orderModel = orderAssembler.toModel(orderService.insert(purchaseDTO));
    return new ResponseEntity<>(orderModel, HttpStatus.CREATED);
  }
}
