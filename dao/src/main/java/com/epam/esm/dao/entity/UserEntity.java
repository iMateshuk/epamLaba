package com.epam.esm.dao.entity;

import com.epam.esm.dao.audit.Auditable;
import com.epam.esm.dao.audit.AuditableListener;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@EntityListeners(AuditableListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "users", schema = "gc")
public class UserEntity implements Serializable, Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "user_name", unique = true, nullable = false)
  private String userName;

  @Column(name = "password", nullable = false)
  private String password;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderEntity> orders;
}
