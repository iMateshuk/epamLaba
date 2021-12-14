package com.epam.esm.service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
  private Integer id;
  private Float cost;
  private Timestamp createdDate;
  private GiftCertificateDTO certificate;
}
