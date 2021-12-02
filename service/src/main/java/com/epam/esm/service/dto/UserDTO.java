package com.epam.esm.service.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
  private Integer id;
  private String userName;
  private List<OrderDTO> orders;
}
