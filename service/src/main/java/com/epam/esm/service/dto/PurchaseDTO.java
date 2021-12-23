package com.epam.esm.service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {
  @NotBlank(message = "app.NotBlank.message")
  @Min(value = 1, message = "app.Min.message")
  @Max(value = Integer.MAX_VALUE,  message = "app.Max.message")
  private Integer userId;

  @NotBlank(message = "app.NotBlank.message")
  @Min(value = 1, message = "app.Min.message")
  @Max(value = Integer.MAX_VALUE,  message = "app.Max.message")
  private Integer certId;
}
