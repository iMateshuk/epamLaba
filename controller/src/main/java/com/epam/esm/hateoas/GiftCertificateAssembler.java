package com.epam.esm.hateoas;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.dto.PurchaseDTO;
import com.epam.esm.service.util.ServiceConvertor;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@Component
public class GiftCertificateAssembler implements RepresentationModelAssembler<GiftCertificateDTO, GiftCertificateModel> {
  private final ServiceConvertor convertor;
  private final TagAssembler tagAssembler;

  @Override
  public GiftCertificateModel toModel(GiftCertificateDTO certificateDTO) {
    GiftCertificateModel certificateModel = convertor.toTarget(certificateDTO, GiftCertificateModel.class);
    certificateModel.setTags(
        certificateModel.getTags().stream().map(tagAssembler::addLinkToModel).collect(Collectors.toList())
    );
    return addLinkToModel(certificateModel);
  }

  public List<GiftCertificateModel> toModel(List<GiftCertificateDTO> certificates) {
    return certificates.stream().map(this::toModel).collect(Collectors.toList());
  }

  public GiftCertificateModel addLinkToModel(GiftCertificateModel certificateModel) {
    certificateModel.add(linkTo(methodOn(GiftCertificateController.class)
        .findById(certificateModel.getId())).withSelfRel());
    certificateModel.add(linkTo(methodOn(GiftCertificateController.class)
        .delete(certificateModel.getId())).withRel("delete"));
    certificateModel.add(linkTo(methodOn(OrderController.class)
        .insert(new PurchaseDTO())).withRel("buy"));
    return certificateModel;
  }
}
