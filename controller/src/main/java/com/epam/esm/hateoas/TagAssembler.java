package com.epam.esm.hateoas;

import com.epam.esm.controller.TagController;
import com.epam.esm.service.dto.TagDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@Component
public class TagAssembler implements RepresentationModelAssembler<TagDTO, TagModel> {
  private final ModelMapper modelMapper;

  @Override
  public TagModel toModel(TagDTO tagDTO) {
    TagModel tagModel = modelMapper.map(tagDTO, TagModel.class);
    return addLinkToModel(tagModel);
  }

  public TagModel addLinkToModel(TagModel tagModel) {
    tagModel.add(linkTo(methodOn(TagController.class).findById(tagModel.getId())).withSelfRel());
    tagModel.add(linkTo(methodOn(TagController.class).deleteById(tagModel.getId())).withRel("delete"));
    return tagModel;
  }
}
