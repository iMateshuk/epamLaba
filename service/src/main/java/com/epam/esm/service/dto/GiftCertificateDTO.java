package com.epam.esm.service.dto;

import com.epam.esm.service.validation.OnCertificateCreate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateDTO {

  private Integer id;

  @NotBlank(message = "app.NotBlank.message", groups = OnCertificateCreate.class)
  @Size(min = 3, max = 30, message = "app.Size.message")
  @Pattern(regexp = "[\\w+( )?]+", message = "field.match.error")
  private String name;

  @NotBlank(message = "app.NotBlank.message", groups = OnCertificateCreate.class)
  @Size(min = 3, max = 30, message = "app.Size.message")
  @Pattern(regexp = "[\\w+( )?]+", message = "field.match.error")
  private String description;

  @NotNull(message = "app.NotNull.message", groups = OnCertificateCreate.class)
  @DecimalMax(value = "9999.0", message = "app.DecimalMax.message")
  @DecimalMin(value = "1.0", message = "app.DecimalMin.message")
  private Float price;

  @NotNull(message = "app.NotNull.message", groups = OnCertificateCreate.class)
  @Min(value = 1, message = "app.Min.message")
  @Max(value = Integer.MAX_VALUE, message = "app.Max.message")
  private Integer duration;

  private String createdDate;
  private String modifiedDate;

  @Valid
  private List<TagDTO> tags;
}
