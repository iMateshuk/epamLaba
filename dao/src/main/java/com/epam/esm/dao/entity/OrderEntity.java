package com.epam.esm.dao.entity;

import com.epam.esm.dao.audit.Auditable;
import com.epam.esm.dao.audit.AuditableListener;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@EntityListeners(AuditableListener.class)
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

  @CreationTimestamp
  @Column(name = "create_date", nullable = false, updatable = false)
  private Timestamp createDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cert_id", nullable = false)
  private GiftCertificateEntity certificate;
}
