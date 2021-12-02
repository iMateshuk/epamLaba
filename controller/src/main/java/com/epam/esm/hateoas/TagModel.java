package com.epam.esm.hateoas;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "tags")
public class TagModel extends RepresentationModel<TagModel> {
  private Integer id;
  private String name;
}
