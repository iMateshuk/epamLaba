package com.epam.esm.hateoas;

import com.epam.esm.service.dto.RoleDTO;
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
@Relation(collectionRelation = "users")
public class UserModel extends RepresentationModel<UserModel> {
  private Integer id;
  private String login;
  private List<RoleDTO> roles;
  private List<OrderModel> orders;
}
