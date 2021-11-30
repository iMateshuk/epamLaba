package com.epam.esm.service.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDTO {
  private Integer id;
  private String userName;
  private List<OrderDTO> orders;
}
