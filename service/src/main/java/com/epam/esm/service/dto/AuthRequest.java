package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@Builder
public class AuthRequest {

  @NotBlank
  @Size(min = 3, max = 30, message = "app.Size.message")
  private String login;

  @NotBlank
  @Size(min = 3, max = 30, message = "app.Size.message")
  private String password;
}
