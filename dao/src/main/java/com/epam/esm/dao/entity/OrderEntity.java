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

@EntityListeners(AuditListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Order")
@Table(name = "orders", schema = "gc")
public class OrderEntity implements Serializable, Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "cost", nullable = false)
  private Float cost;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cert_id", nullable = false)
  private GiftCertificateEntity certificate;

  @CreationTimestamp
  @Column(name = "created_date", nullable = false, updatable = false)
  private Timestamp createdDate;

  @UpdateTimestamp
  @Column(name = "modified_date", nullable = false)
  private Timestamp modifiedDate;
}
