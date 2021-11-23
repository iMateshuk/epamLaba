package com.epam.esm.dao.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class GiftCertificateEntity implements Serializable {

  private int id;
  private String name;
  private String description;
  private float price;
  private int duration;
  private Timestamp createDate;
  private Timestamp lastUpdateDate;

  public GiftCertificateEntity() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public Timestamp getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Timestamp createDate) {
    this.createDate = createDate;
  }

  public Timestamp getLastUpdateDate() {
    return lastUpdateDate;
  }

  public void setLastUpdateDate(Timestamp lastUpdateDate) {
    this.lastUpdateDate = lastUpdateDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GiftCertificateEntity giftCert = (GiftCertificateEntity) o;
    return id == giftCert.id && Float.compare(giftCert.price, price) == 0 && duration == giftCert.duration && Objects.equals(name, giftCert.name) && Objects.equals(description, giftCert.description) && Objects.equals(createDate, giftCert.createDate) && Objects.equals(lastUpdateDate, giftCert.lastUpdateDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, price, duration, createDate, lastUpdateDate);
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
        '}';
  }
}
