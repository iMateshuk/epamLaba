package com.epam.esm.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity(name = "Tag")
@Table(name = "tags", schema = "gc")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagEntity implements Serializable {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TagEntity tagEntity = (TagEntity) o;
    return Objects.equals(id, tagEntity.id)
        && Objects.equals(name, tagEntity.name) && Objects.equals(certs, tagEntity.certs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, certs);
  }

  @Override
  public String toString() {
    return getClass().getName() + "{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", certs=" + certs +
        '}';
  }
}
