package com.epam.esm.hateoas;

import com.epam.esm.controller.OrderController;
import com.epam.esm.service.dto.OrderDTO;
import com.epam.esm.service.util.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@Component
public class OrderAssembler implements RepresentationModelAssembler<OrderDTO, OrderModel> {
  private final Mapper mapper;
  private final GiftCertificateAssembler certificateAssembler;
  private final TagAssembler tagAssembler;

  @Override
  public OrderModel toModel(OrderDTO orderDTO) {
    OrderModel orderModel = mapper.toTarget(orderDTO, OrderModel.class);
    GiftCertificateModel certificateModel = orderModel.getCertificate();
    orderModel.setCertificate(certificateAssembler.addLinkToModel(certificateModel));
    certificateModel.setTags(
        certificateModel.getTags().stream().map(tagAssembler::addLinkToModel).collect(Collectors.toList())
    );
    return addLinkToModel(orderModel);
  }

  public List<OrderModel> toModel(List<OrderDTO> orders) {
    return orders.stream().map(this::toModel).collect(Collectors.toList());
  }

  public OrderModel addLinkToModel(OrderModel orderModel) {
    orderModel.add(linkTo(methodOn(OrderController.class).findById(orderModel.getId())).withSelfRel());
    return orderModel;
  }
}
