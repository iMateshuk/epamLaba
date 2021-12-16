package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class AuthResponse {
  private Integer id;
  private String login;
  private List<RoleDTO> roles;
  private String token;
}
