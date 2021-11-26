package com.epam.esm.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity(name = "Certificate")
@Table(name = "gift_certificate", schema = "gc")
@Getter
@Setter
@AllArgsConstructor
public class GiftCertificateEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private float price;

  @Column(nullable = false)
  private int duration;

  @CreationTimestamp
  @Column(name = "create_date", nullable = false, updatable = false)
  private Timestamp createDate;

  @UpdateTimestamp
  @Column(name = "last_update_date", nullable = false)
  private Timestamp lastUpdateDate;

  @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
  @JoinTable(name = "gc_tag", schema = "gc",
      joinColumns = @JoinColumn(name = "gc_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
  private List<TagEntity> tags;

  public GiftCertificateEntity() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GiftCertificateEntity that = (GiftCertificateEntity) o;
    return id == that.id && Float.compare(that.price, price) == 0 && duration == that.duration && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(createDate, that.createDate) && Objects.equals(lastUpdateDate, that.lastUpdateDate) && Objects.equals(tags, that.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, price, duration, createDate, lastUpdateDate, tags);
  }

  @Override
  public String toString() {
    return getClass().getName() + "{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", price=" + price +
        ", duration=" + duration +
        ", createDate=" + createDate +
        ", lastUpdateDate=" + lastUpdateDate +
        ", tags=" + tags +
        '}';
  }
}
