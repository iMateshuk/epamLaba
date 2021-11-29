package com.epam.esm.service.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
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
