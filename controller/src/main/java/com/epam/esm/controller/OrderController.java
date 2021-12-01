package com.epam.esm.controller;

import com.epam.esm.service.OrderService;
import com.epam.esm.service.dto.OrderDTO;
import com.epam.esm.service.dto.PurchaseDTO;
import com.epam.esm.service.util.Validator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
  private final OrderService orderService;
  private final Validator validator;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public OrderDTO insert(@RequestBody PurchaseDTO purchaseDTO) {
    validator.checkPurchaseDTO(purchaseDTO);
    return orderService.insert(purchaseDTO);
  }
}
