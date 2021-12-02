package com.epam.esm.service.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {
  private Integer userId;
  private Integer certId;
}
