package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateDTO {
  private Integer id;
  private String name;
  private String description;
  private Float price;
  private Integer duration;
  private String createDate;
  private String lastUpdateDate;
  private List<TagDTO> tags;
}
