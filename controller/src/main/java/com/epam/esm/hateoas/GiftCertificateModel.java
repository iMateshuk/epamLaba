package com.epam.esm.hateoas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
  private String createdDate;
  private String modifiedDate;
  private List<TagModel> tags;
}
