package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertDTO;

import java.util.List;

public interface ServiceCommon {

    public List<GiftCertDTO> getGiftCertificate(String tagName);
}
