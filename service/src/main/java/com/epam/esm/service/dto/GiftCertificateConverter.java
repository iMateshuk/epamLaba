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

        GiftCertificateDTO gcDTO = new GiftCertificateDTO();

        gcDTO.setId(giftCertificate.getId());
        gcDTO.setName(giftCertificate.getName());
        gcDTO.setDescription(giftCertificate.getDescription());
        gcDTO.setDuration(giftCertificate.getDuration());
        gcDTO.setPrice(giftCertificate.getPrice());
        gcDTO.setLastUpdateDate(giftCertificate.getLastUpdateDate());

        return gcDTO;
    }

    public static List<GiftCertificateEntity> toEntity(List<GiftCertificateDTO> giftCertificates) {

        return giftCertificates.stream().map(GiftCertificateConverter::toEntity)
                .collect(Collectors.toList());
    }

    public static GiftCertificateEntity toEntity(GiftCertificateDTO giftCertificate) {

        GiftCertificateEntity gcEntity = new GiftCertificateEntity();

        gcEntity.setId(giftCertificate.getId());
        gcEntity.setName(giftCertificate.getName());
        gcEntity.setDescription(giftCertificate.getDescription());
        gcEntity.setPrice(giftCertificate.getPrice());
        gcEntity.setDuration(giftCertificate.getDuration());

        return gcEntity;
    }

}
