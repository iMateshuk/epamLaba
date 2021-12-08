package com.epam.esm.dao.entity;

import com.epam.esm.dao.audit.Auditable;
import com.epam.esm.dao.audit.AuditableListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@EntityListeners(AuditableListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Certificate")
@Table(name = "gift_certificate", schema = "gc")
public class GiftCertificateEntity implements Serializable, Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private Float price;

  @Column(nullable = false)
  private Integer duration;

  @CreationTimestamp
  @Column(name = "create_date", nullable = false, updatable = false)
  private Timestamp createDate;

  @UpdateTimestamp
  @Column(name = "last_update_date", nullable = false)
  private Timestamp lastUpdateDate;

  @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
  @JoinTable(name = "gc_tag", schema = "gc",
      joinColumns = @JoinColumn(name = "gc_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
  private List<TagEntity> tags;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "certificate", cascade = CascadeType.MERGE)
  private List<OrderEntity> orders;
}
