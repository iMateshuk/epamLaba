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
  private String nickName;
  private String firstName;
  private String lastName;
  private List<OrderDTO> orders;
}
