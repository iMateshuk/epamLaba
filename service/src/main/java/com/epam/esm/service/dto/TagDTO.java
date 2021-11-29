package com.epam.esm.service.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class TagDTO implements Serializable {
  private Integer id;
  private String name;
}
