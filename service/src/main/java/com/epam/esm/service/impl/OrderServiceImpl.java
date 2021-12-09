package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.entity.OrderEntity;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.dto.OrderDTO;
import com.epam.esm.service.dto.PurchaseDTO;
import com.epam.esm.service.util.Mapper;
import com.epam.esm.service.util.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Order
 * Use for business logic of APP
 *
 * @author Ivan Matsiashuk
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
  private final OrderDAO orderDAO;
  private final Validator validator;
  private final Mapper mapper;

  @Transactional
  @Override
  public OrderDTO insert(PurchaseDTO purchaseDTO) {
    OrderEntity orderEntity = validator.validatePurchaseDto(purchaseDTO);
    return mapper.toTarget(orderDAO.insert(orderEntity), OrderDTO.class);
  }
}
