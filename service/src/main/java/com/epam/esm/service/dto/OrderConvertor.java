package com.epam.esm.service.dto;

import com.epam.esm.dao.entity.OrderEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@AllArgsConstructor
public class OrderConvertor {
  private final ModelMapper modelMapper;

  public OrderDTO toDto(OrderEntity orderEntity) {
    return Objects.isNull(orderEntity) ? null : modelMapper.map(orderEntity, OrderDTO.class);
  }

  public OrderEntity toEntity(OrderDTO orderDTO) {
    return Objects.isNull(orderDTO) ? null : modelMapper.map(orderDTO, OrderEntity.class);
  }

  public List<OrderDTO> toDto(List<OrderEntity> orderEntities) {
    return orderEntities.stream().map(this::toDto).collect(Collectors.toList());
  }

  public List<OrderEntity> toEntity(List<OrderDTO> orderDtos) {
    return orderDtos.stream().map(this::toEntity).collect(Collectors.toList());
  }
}
