package com.epam.esm.hateoas;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "certificates")
public class GiftCertificateModel extends RepresentationModel<GiftCertificateModel> {
  private Integer id;
  private String name;
  private String description;
  private Float price;
  private Integer duration;
  private String createDate;
  private String lastUpdateDate;
  private List<TagModel> tags;
}
