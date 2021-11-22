package com.epam.esm.service.dto;

import com.epam.esm.dao.entity.GiftCertificateEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class GiftCertificateConverter {
  private final static TimeZone tz = TimeZone.getTimeZone("UTC");
  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
  static {
    df.setTimeZone(tz);
  }

  public static List<GiftCertificateDTO> toDto(List<GiftCertificateEntity> giftCertificates) {
    return giftCertificates.stream().map(GiftCertificateConverter::toDto)
        .collect(Collectors.toList());
  }

  public static GiftCertificateDTO toDto(GiftCertificateEntity giftCertificate) {
    GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO();

    giftCertificateDTO.setId(giftCertificate.getId());
    giftCertificateDTO.setName(giftCertificate.getName());
    giftCertificateDTO.setDescription(giftCertificate.getDescription());
    giftCertificateDTO.setDuration(giftCertificate.getDuration());
    giftCertificateDTO.setPrice(giftCertificate.getPrice());
    giftCertificateDTO.setCreateDate(df.format(giftCertificate.getCreateDate()));
    giftCertificateDTO.setLastUpdateDate(df.format(giftCertificate.getLastUpdateDate()));
    return giftCertificateDTO;
  }

  public static List<GiftCertificateEntity> toEntity(List<GiftCertificateDTO> giftCertificates) {
    return giftCertificates.stream().map(GiftCertificateConverter::toEntity)
        .collect(Collectors.toList());
  }

  public static GiftCertificateEntity toEntity(GiftCertificateDTO giftCertificate) {
    GiftCertificateEntity giftCertificateEntity = new GiftCertificateEntity();

    giftCertificateEntity.setId(giftCertificate.getId());
    giftCertificateEntity.setName(giftCertificate.getName());
    giftCertificateEntity.setDescription(giftCertificate.getDescription());
    giftCertificateEntity.setPrice(giftCertificate.getPrice());
    giftCertificateEntity.setDuration(giftCertificate.getDuration());
    return giftCertificateEntity;
  }
}
