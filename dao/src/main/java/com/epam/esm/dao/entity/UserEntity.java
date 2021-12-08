package com.epam.esm.dao.entity;

import com.epam.esm.dao.audit.AuditListener;
import com.epam.esm.dao.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@EntityListeners(AuditListener.class)
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

  @CreationTimestamp
  @Column(name = "created_date", nullable = false, updatable = false)
  private Timestamp createdDate;

  @UpdateTimestamp
  @Column(name = "modified_date", nullable = false)
  private Timestamp modifiedDate;
}
