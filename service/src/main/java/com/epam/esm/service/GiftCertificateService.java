package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertificateDTO;

import java.util.List;

public interface GiftCertificateService {

    public void createGiftCertificate(GiftCertificateDTO gcDTO);

    public List<GiftCertificateDTO> searchGiftCertificates();

    public GiftCertificateDTO searchGiftCertificate(int id);

    public void updateGiftCertificate(GiftCertificateDTO gcDTO);

    public void delGiftCertificate(int id);
}
