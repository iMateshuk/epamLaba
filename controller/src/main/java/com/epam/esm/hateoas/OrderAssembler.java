package com.epam.esm.hateoas;

import com.epam.esm.service.dto.OrderDTO;
import com.epam.esm.util.ControllerConvertor;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class OrderAssembler implements RepresentationModelAssembler<OrderDTO, OrderModel> {
  private final ControllerConvertor convertor;
  private final GiftCertificateAssembler certificateAssembler;
  private final TagAssembler tagAssembler;

  @Override
  public OrderModel toModel(OrderDTO orderDTO) {
    OrderModel orderModel = convertor.toTarget(orderDTO, OrderModel.class);
    GiftCertificateModel certificateModel = orderModel.getCertificate();
    orderModel.setCertificate(certificateAssembler.addLinkToModel(certificateModel));
    certificateModel.setTags(
        certificateModel.getTags().stream().map(tagAssembler::addLinkToModel).collect(Collectors.toList())
    );
    return orderModel;
  }

  public List<OrderModel> toModels(List<OrderDTO> orders) {
    return orders.stream().map(this::toModel).collect(Collectors.toList());
  }
}
