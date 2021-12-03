package com.epam.esm.hateoas;

import com.epam.esm.controller.UserController;
import com.epam.esm.service.dto.OrderDTO;
import com.epam.esm.service.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@Component
public class UserAssembler implements RepresentationModelAssembler<UserDTO, UserModel> {
  private final ModelMapper modelMapper;
  private final GiftCertificateAssembler certificateAssembler;
  private final TagAssembler tagAssembler;

  @Override
  public UserModel toModel(UserDTO userDTO) {
    UserModel userModel = modelMapper.map(userDTO, UserModel.class);

    userModel.setOrders(toOrderModel(userDTO.getOrders(), userModel));
    userModel.getOrders().forEach(order -> {
      GiftCertificateModel certificateModel = order.getCertificate();
      order.setCertificate(certificateAssembler.addLinkToModel(certificateModel));
      certificateModel.setTags(
          certificateModel.getTags().stream().map(tagAssembler::addLinkToModel).collect(Collectors.toList())
      );
    });
    return addLinkToModel(userModel);
  }

  public List<UserModel> toModels(List<UserDTO> users) {
    return users.stream().map(this::toModel).collect(Collectors.toList());
  }

  private List<OrderModel> toOrderModel(List<OrderDTO> orders, UserModel userModel) {
    return orders.stream()
        .map(order -> {
          OrderModel orderModel = modelMapper.map(order, OrderModel.class);
          orderModel.add(linkTo(methodOn(UserController.class).findByIdOrder(userModel.getId(), order.getId())).withSelfRel());
          return orderModel;
        })
        .collect(Collectors.toList());
  }

  public UserModel addLinkToModel(UserModel userModel) {
    userModel.add(linkTo(methodOn(UserController.class).findById(userModel.getId())).withSelfRel());
    userModel.add(linkTo(methodOn(UserController.class).findByIdOrders(userModel.getId())).withRel("orders"));
    return userModel;
  }
}
