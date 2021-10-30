package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertificateDTO;

import java.util.List;

public interface ServiceCommon {

    public List<GiftCertificateDTO> getGiftCertificate(String tagName);
}
