package com.epam.esm.hateoas;

import com.epam.esm.controller.TagController;
import com.epam.esm.service.dto.TagDTO;
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
public class TagAssembler implements RepresentationModelAssembler<TagDTO, TagModel> {
  private final Mapper mapper;

  @Override
  public TagModel toModel(TagDTO tagDTO) {
    TagModel tagModel = mapper.toTarget(tagDTO, TagModel.class);
    return addLinkToModel(tagModel);
  }

  public List<TagModel> toModel(List<TagDTO> tags) {
    return tags.stream().map(this::toModel).collect(Collectors.toList());
  }

  public TagModel addLinkToModel(TagModel tagModel) {
    tagModel.add(linkTo(methodOn(TagController.class).findById(tagModel.getId())).withSelfRel());
    tagModel.add(linkTo(methodOn(TagController.class).deleteById(tagModel.getId())).withRel("delete"));
    return tagModel;
  }
}
