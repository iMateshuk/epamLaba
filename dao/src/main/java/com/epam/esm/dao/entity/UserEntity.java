package com.epam.esm.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "users", schema = "gc")
public class UserEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "user_name", unique = true, nullable = false)
  private String userName;

  @Column(name = "password", nullable = false)
  private String password;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderEntity> orders;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserEntity that = (UserEntity) o;
    return Objects.equals(id, that.id) && Objects.equals(userName, that.userName)
        && Objects.equals(password, that.password) && Objects.equals(orders, that.orders);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userName, password, orders);
  }
}
