package com.epam.esm.service;

import com.epam.esm.service.dto.OrderDTO;
import com.epam.esm.service.dto.PurchaseDTO;

public interface OrderService {

  OrderDTO insert(PurchaseDTO purchaseDTO);
}
