package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificateDTO {
  private Integer id;
  private String name;
  private String description;
  private Float price;
  private Integer duration;
  private String createDate;
  private String lastUpdateDate;
  private List<TagDTO> tags;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GiftCertificateDTO that = (GiftCertificateDTO) o;
    return Objects.equals(id, that.id) && Objects.equals(name, that.name)
        && Objects.equals(description, that.description) && Objects.equals(price, that.price)
        && Objects.equals(duration, that.duration) && Objects.equals(createDate, that.createDate)
        && Objects.equals(lastUpdateDate, that.lastUpdateDate) && Objects.equals(tags, that.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, price, duration, createDate, lastUpdateDate, tags);
  }

  @Override
  public String toString() {

    return getClass().getName() + "{" +
        "id=" + id +
        ", name='" + name +
        ", description='" + description +
        ", price=" + price +
        ", duration=" + duration +
        ", createDate=" + createDate +
        ", lastUpdateDate=" + lastUpdateDate +
        ", tags=" + tags +
        '}';
  }
}
