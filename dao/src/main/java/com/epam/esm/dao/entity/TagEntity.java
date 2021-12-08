package com.epam.esm.dao.entity;

import com.epam.esm.dao.audit.Auditable;
import com.epam.esm.dao.audit.AuditableListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@EntityListeners(AuditableListener.class)
@Entity(name = "Tag")
@Table(name = "tags", schema = "gc")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagEntity implements Serializable, Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(unique = true, nullable = false)
  private String name;

  @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE}, mappedBy = "tags")
  private List<GiftCertificateEntity> certs;

  public TagEntity(String name) {
    this.name = name;
  }
}
