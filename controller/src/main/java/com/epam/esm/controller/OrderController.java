package com.epam.esm.controller;

import com.epam.esm.hateoas.OrderAssembler;
import com.epam.esm.hateoas.OrderModel;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.dto.PurchaseDTO;
import com.epam.esm.service.util.Validator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
  private final OrderService orderService;
  private final Validator validator;
  private final OrderAssembler orderAssembler;

  @PostMapping
  public ResponseEntity<OrderModel> insert(@RequestBody PurchaseDTO purchaseDTO) {
    validator.validatePurchaseDto(purchaseDTO);
    OrderModel orderModel = orderAssembler.toModel(orderService.insert(purchaseDTO));
    orderModel.add(linkTo(methodOn(OrderController.class).insert(purchaseDTO)).withSelfRel());
    return new ResponseEntity<>(orderModel, HttpStatus.CREATED);
  }
}
