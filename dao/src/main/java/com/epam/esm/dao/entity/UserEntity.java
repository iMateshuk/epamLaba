package com.epam.esm.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

  @Column(name = "nick_name", unique = true, nullable = false)
  private String nickName;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderEntity> orders;

  public UserEntity addOrder(OrderEntity order) {
    orders.add(order);
    order.setUserEntity(this);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserEntity that = (UserEntity) o;
    return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName)
        && Objects.equals(lastName, that.lastName) && Objects.equals(nickName, that.nickName)
        && Objects.equals(orders, that.orders);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, nickName, orders);
  }

  @Override
  public String toString() {
    return getClass().getName() +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", nickname='" + nickName + '\'' +
        ", orders=" + orders +
        '}';
  }
}
