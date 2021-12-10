package com.epam.esm.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Certificate")
@Table(name = "gift_certificate", schema = "gc")
@Audited
public class GiftCertificateEntity implements Serializable {

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

  @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
  @JoinTable(name = "gc_tag", schema = "gc",
      joinColumns = @JoinColumn(name = "gc_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
  private List<TagEntity> tags;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "certificate", cascade = CascadeType.MERGE)
  private List<OrderEntity> orders;

  @CreationTimestamp
  @Column(name = "created_date", nullable = false, updatable = false)
  private Timestamp createdDate;

  @UpdateTimestamp
  @Column(name = "modified_date", nullable = false)
  private Timestamp modifiedDate;
}
