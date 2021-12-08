package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateDTO {
  private Integer id;

  @NotBlank(message = "app.NotBlank.message")
  @Size(min = 3, max = 30, message = "app.Size.message")
  private String name;

  @NotBlank(message = "app.NotBlank.message")
  @Size(min = 3, max = 30, message = "app.Size.message")
  private String description;

  @NotNull(message = "app.NotNull.message")
  @DecimalMax(value = "9999.0", message = "app.DecimalMax.message")
  @DecimalMin(value = "1.0", message = "app.DecimalMin.message")
  private Float price;

  @NotNull(message = "app.NotNull.message")
  @Min(value = 1, message = "app.Min.message")
  @Max(value = Integer.MAX_VALUE,  message = "app.Max.message")
  private Integer duration;

  private String createdDate;
  private String modifiedDate;
  private List<TagDTO> tags;
}
