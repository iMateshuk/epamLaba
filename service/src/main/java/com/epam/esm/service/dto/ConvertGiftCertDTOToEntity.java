package com.epam.esm.service.dto;

import com.epam.esm.dao.entity.GiftCertEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertGiftCertDTOToEntity {

    public static List<GiftCertEntity> getGiftCertEntity(List<GiftCertRequestDTO> listGiftCert) {

        return listGiftCert.stream().map(ConvertGiftCertDTOToEntity::getGiftCertEntity)
                .collect(Collectors.toList());
    }

    public static GiftCertEntity getGiftCertEntity(GiftCertRequestDTO gc) {

        GiftCertEntity gcEntity = new GiftCertEntity();

        gcEntity.setId(gc.getId());
        gcEntity.setName(gc.getName());
        gcEntity.setDescription(gc.getDescription());
        gcEntity.setPrice(gc.getPrice());
        gcEntity.setDuration(gc.getDuration());

        return gcEntity;
    }

}
