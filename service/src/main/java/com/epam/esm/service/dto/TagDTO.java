package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO implements Serializable {
  private Integer id;
  private String name;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TagDTO tagDTO = (TagDTO) o;
    return Objects.equals(id, tagDTO.id) && Objects.equals(name, tagDTO.name);
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
