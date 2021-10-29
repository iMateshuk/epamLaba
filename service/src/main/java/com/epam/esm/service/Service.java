package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertificateDTO;

import java.util.List;

public interface Service {

    public String list();

    public List<GiftCertificateDTO> listGift();

    public List<GiftCertificateDTO> getGiftCertificate(String tagName);
}
