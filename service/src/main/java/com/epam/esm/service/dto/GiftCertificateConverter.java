package com.epam.esm.service.dto;

import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.entity.TagEntity;

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
    giftCertificateDTO.setTags(convertTagEntityToTagDto(giftCertificate.getTags()));
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
    List<TagDTO> tags = giftCertificate.getTags();
    if (tags != null) {
      giftCertificateEntity.setTags(convertTagDtoToTagEntity(tags));
    }
    return giftCertificateEntity;
  }

  private static List<TagDTO> convertTagEntityToTagDto(List<TagEntity> tags) {
    return tags.stream()
        .filter((tagEntity) -> tagEntity.getName() != null)
        .map(TagConverter::toDto)
        .collect(Collectors.toList());
  }

  private static List<TagEntity> convertTagDtoToTagEntity(List<TagDTO> tags) {
    return tags.stream()
        .filter((tagDTO) -> tagDTO.getName() != null)
        .map(TagConverter::toEntity)
        .collect(Collectors.toList());
  }
}
