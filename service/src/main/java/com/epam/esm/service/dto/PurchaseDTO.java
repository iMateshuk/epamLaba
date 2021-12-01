package com.epam.esm.service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PurchaseDTO {
  private Integer userId;
  private Integer certId;
}
