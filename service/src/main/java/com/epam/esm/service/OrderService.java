package com.epam.esm.service;

import com.epam.esm.service.dto.OrderDTO;
import com.epam.esm.service.dto.PurchaseDTO;

public interface OrderService {

  /**
   *
   * @param id
   * @return order
   */
  OrderDTO findById(Integer id);

  /**
   *
   * @param purchaseDTO
   * @return new order
   */
  OrderDTO insert(PurchaseDTO purchaseDTO);
}
