package com.epam.esm.service.dto;

import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
  private Integer id;
  private Float cost;
  private Timestamp createDate;
  private GiftCertificateDTO certificate;
}
