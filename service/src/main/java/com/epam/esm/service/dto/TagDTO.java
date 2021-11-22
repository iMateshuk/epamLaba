package com.epam.esm.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class TagDTO implements Serializable {
  private int id;
  private String name;

  public TagDTO() {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TagDTO tagDTO = (TagDTO) o;
    return id == tagDTO.id && Objects.equals(name, tagDTO.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    return getClass().getName() + "{" +
        "id=" + id +
        ", name='" + name +
        '}';
  }
}
