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
@Relation(collectionRelation = "users")
public class UserModel extends RepresentationModel<UserModel> {
  private Integer id;
  private String userName;
  private List<OrderModel> orders;
}
