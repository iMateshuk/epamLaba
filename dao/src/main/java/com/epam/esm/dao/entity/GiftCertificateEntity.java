package com.epam.esm.dao.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Certificate")
@Table(name = "gift_certificate", schema = "gc")
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

  @OneToMany(mappedBy = "certificate", cascade = CascadeType.MERGE)
  private List<OrderEntity> orders;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GiftCertificateEntity that = (GiftCertificateEntity) o;
    return Objects.equals(id, that.id)
        && Objects.equals(name, that.name) && Objects.equals(description, that.description)
        && Objects.equals(price, that.price) && Objects.equals(duration, that.duration)
        && Objects.equals(createDate, that.createDate) && Objects.equals(lastUpdateDate, that.lastUpdateDate)
        && Objects.equals(tags, that.tags);
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
