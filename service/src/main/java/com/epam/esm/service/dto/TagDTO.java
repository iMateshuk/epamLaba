package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO implements Serializable {
  private Integer id;

  @NotBlank
  @Size(min = 3, max = 30)
  private String name;
}
