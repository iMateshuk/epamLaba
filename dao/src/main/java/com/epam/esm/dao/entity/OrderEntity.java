package com.epam.esm.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Order")
@Table(name = "orders", schema = "gc")
public class OrderEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "cost", nullable = false)
  private Float cost;

  @CreationTimestamp
  @Column(name = "create_date", nullable = false, updatable = false)
  private Timestamp createDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity userEntity;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderEntity that = (OrderEntity) o;
    return Objects.equals(id, that.id) && Objects.equals(cost, that.cost)
        && Objects.equals(createDate, that.createDate) && Objects.equals(userEntity, that.userEntity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, cost, createDate, userEntity);
  }

  @Override
  public String toString() {
    return getClass().getName() +
        "id=" + id +
        ", cost=" + cost +
        ", createDate=" + createDate +
        ", userEntity=" + userEntity +
        '}';
  }
}
