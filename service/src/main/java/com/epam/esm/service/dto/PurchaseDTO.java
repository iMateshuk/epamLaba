package com.epam.esm.service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {

  @NotNull(message = "app.NotNull.message")
  @Min(value = 1, message = "app.Min.message")
  @Max(value = Integer.MAX_VALUE,  message = "app.Max.message")
  private Integer userId;

  @NotNull(message = "app.NotNull.message")
  @Min(value = 1, message = "app.Min.message")
  @Max(value = Integer.MAX_VALUE,  message = "app.Max.message")
  private Integer certId;
}
