package com.epam.esm.service.dto;

import com.epam.esm.dao.entity.GiftCertificateEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertDTO {

    public static List<GiftCertificateDTO> getGiftCertDTO(List<GiftCertificateEntity> listGiftCert) {

        return listGiftCert.stream().map(ConvertDTO::convertToGiftCertDTO)
                .collect(Collectors.toList());
    }

    private static GiftCertificateDTO convertToGiftCertDTO(GiftCertificateEntity gc) {

        GiftCertificateDTO gcDTO = new GiftCertificateDTO();

        gcDTO.setId(gc.getId());
        gcDTO.setName(gc.getName());
        gcDTO.setDescription(gc.getDescription());
        gcDTO.setDuration(gc.getDuration());
        gcDTO.setPrice(gc.getPrice());

        return gcDTO;
    }

}
