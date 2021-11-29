package com.epam.esm.service.dto;

import com.epam.esm.dao.entity.GiftCertificateEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import java.util.stream.Collectors;

@AllArgsConstructor
public class GiftCertificateConverter {
  private final static TimeZone tz = TimeZone.getTimeZone("UTC");
  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

  static {
    df.setTimeZone(tz);
  }

  private final ModelMapper modelMapper;
  private final TagConverter tagConverter;

  public List<GiftCertificateDTO> toDto(List<GiftCertificateEntity> giftCertificates) {
    return giftCertificates.stream().map(this::toDto).collect(Collectors.toList());
  }

  public GiftCertificateDTO toDto(GiftCertificateEntity certificateEntity) {
    return Objects.isNull(certificateEntity) ? null : modelMapper.map(certificateEntity, GiftCertificateDTO.class);
  }

  public List<GiftCertificateEntity> toEntity(List<GiftCertificateDTO> giftCertificates) {
    return giftCertificates.stream().map(this::toEntity).collect(Collectors.toList());
  }

  public GiftCertificateEntity toEntity(GiftCertificateDTO certificateDTO) {
    return Objects.isNull(certificateDTO) ? null : modelMapper.map(certificateDTO, GiftCertificateEntity.class);
  }
}
