package com.epam.esm.service.dto;

import com.epam.esm.dao.entity.TagEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
public class TagConverter {
  private final ModelMapper modelMapper;

  public TagDTO toDto(TagEntity tagEntity) {
    return Objects.isNull(tagEntity) ? null : modelMapper.map(tagEntity, TagDTO.class);
  }

  public TagEntity toEntity(TagDTO tagDTO) {
    return Objects.isNull(tagDTO) ? null : modelMapper.map(tagDTO, TagEntity.class);
  }

  public List<TagDTO> toDto(List<TagEntity> tagEntities) {
    return tagEntities.stream().map(this::toDto).collect(Collectors.toList());
  }

  public List<TagEntity> toEntity(List<TagDTO> tags) {
    return tags.stream().map(this::toEntity).collect(Collectors.toList());
  }
}
