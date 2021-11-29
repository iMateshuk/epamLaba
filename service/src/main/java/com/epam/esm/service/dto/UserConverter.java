package com.epam.esm.service.dto;

import com.epam.esm.dao.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UserConverter {
  private final ModelMapper modelMapper;

  public UserDTO toDto(UserEntity userEntity) {
    return Objects.isNull(userEntity) ? null : modelMapper.map(userEntity, UserDTO.class);
  }

  public UserEntity toEntity(UserDTO userDTO) {
    return Objects.isNull(userDTO) ? null : modelMapper.map(userDTO, UserEntity.class);
  }

  public List<UserDTO> toDto(List<UserEntity> userEntities) {
    return userEntities.stream().map(this::toDto).collect(Collectors.toList());
  }

  public List<UserEntity> toEntity(List<UserDTO> userDtos) {
    return userDtos.stream().map(this::toEntity).collect(Collectors.toList());
  }
}
