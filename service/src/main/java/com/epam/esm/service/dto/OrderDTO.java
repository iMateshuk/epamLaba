package com.epam.esm.service.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class OrderDTO {
  private Integer id;
  private Float cost;
  private Timestamp createDate;
  private GiftCertificateDTO certificate;
}
