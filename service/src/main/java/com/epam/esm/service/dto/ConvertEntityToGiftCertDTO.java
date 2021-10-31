package com.epam.esm.service.dto;

import com.epam.esm.dao.entity.GiftCertEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertEntityToGiftCertDTO {

    public static List<GiftCertDTO> getGiftCertDTO(List<GiftCertEntity> listGiftCert) {

        return listGiftCert.stream().map(ConvertEntityToGiftCertDTO::getGiftCertDTO)
                .collect(Collectors.toList());
    }

    public static GiftCertDTO getGiftCertDTO(GiftCertEntity gc) {

        GiftCertDTO gcDTO = new GiftCertDTO();

        gcDTO.setId(gc.getId());
        gcDTO.setName(gc.getName());
        gcDTO.setDescription(gc.getDescription());
        gcDTO.setDuration(gc.getDuration());
        gcDTO.setPrice(gc.getPrice());
        gcDTO.setLastUpdateDate(gc.getLastUpdateDate());

        return gcDTO;
    }

}
