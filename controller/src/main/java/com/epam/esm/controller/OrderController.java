package com.epam.esm.controller;

import com.epam.esm.hateoas.OrderAssembler;
import com.epam.esm.hateoas.OrderModel;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.dto.PurchaseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/orders")
public class OrderController {
  private final OrderService orderService;
  private final OrderAssembler orderAssembler;

  @PostMapping
  public ResponseEntity<OrderModel> insert(@Validated @RequestBody PurchaseDTO purchaseDTO) {
    OrderModel orderModel = orderAssembler.toModel(orderService.insert(purchaseDTO));
    orderModel.add(linkTo(methodOn(OrderController.class).insert(purchaseDTO)).withSelfRel());
    return new ResponseEntity<>(orderModel, HttpStatus.CREATED);
  }
}
