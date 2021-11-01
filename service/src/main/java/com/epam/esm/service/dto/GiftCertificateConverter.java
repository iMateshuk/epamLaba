package com.epam.esm.service.dto;

import com.epam.esm.dao.entity.GiftCertificateEntity;

import java.util.List;
import java.util.stream.Collectors;

public class GiftCertificateConverter {

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
        giftCertificateDTO.setLastUpdateDate(giftCertificate.getLastUpdateDate());

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
