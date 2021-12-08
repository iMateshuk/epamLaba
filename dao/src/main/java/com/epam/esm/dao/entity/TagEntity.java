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
@Entity(name = "Tag")
@Table(name = "tags", schema = "gc")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagEntity implements Serializable, Auditable {

  public TagEntity(String name) {
    this.name = name;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(unique = true, nullable = false)
  private String name;

  @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE}, mappedBy = "tags")
  private List<GiftCertificateEntity> certs;

  @CreationTimestamp
  @Column(name = "created_date", nullable = false, updatable = false)
  private Timestamp createdDate;

  @UpdateTimestamp
  @Column(name = "modified_date", nullable = false)
  private Timestamp modifiedDate;
}
