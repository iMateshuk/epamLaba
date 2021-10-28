package com.epam.esm.service.dto;

import com.epam.esm.dao.entity.GCAndTagName;
import com.epam.esm.dao.entity.GiftCert;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertDTO {

    public static List<GiftCertDTO> getGiftCertDTO(List<GiftCert> listGiftCert) {
        return listGiftCert.stream().map(ConvertDTO::convertToGiftCertDTO)
                .collect(Collectors.toList());
    }

    private static GiftCertDTO convertToGiftCertDTO(GiftCert gc) {

        GiftCertDTO gcDTO = new GiftCertDTO();

        gcDTO.setId(gc.getId());
        gcDTO.setName(gc.getName());
        gcDTO.setDescription(gc.getDescription());
        gcDTO.setDuration(gc.getDuration());
        gcDTO.setPrice(gc.getPrice());

        return gcDTO;
    }

}
