package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.dto.OrderConvertor;
import com.epam.esm.service.dto.OrderDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
  private final OrderDAO orderDAO;
  private final OrderConvertor orderConvertor;

  @Override
  public OrderDTO insert(OrderDTO orderDTO) {
    return orderConvertor.toDto(orderDAO.insert(orderConvertor.toEntity(orderDTO)));
  }
}
