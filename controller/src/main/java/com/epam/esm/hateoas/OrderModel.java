package com.epam.esm.hateoas;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "orders")
public class OrderModel extends RepresentationModel<OrderModel> {
  private Integer id;
  private Float cost;
  private Timestamp createDate;
  private GiftCertificateModel certificate;
}