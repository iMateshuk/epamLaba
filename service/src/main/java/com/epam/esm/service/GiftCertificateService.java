package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertificateDTO;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificateDTO createGiftCertificate(GiftCertificateDTO gcDTO);

    List<GiftCertificateDTO> searchGiftCertificates();

    GiftCertificateDTO searchGiftCertificate(int id);

    List<GiftCertificateDTO> getGiftCertificates(String tagName);

    GiftCertificateDTO updateGiftCertWithTags(GiftCertificateDTO giftCertificateDTO);

    GiftCertificateDTO updateGiftCertificate(GiftCertificateDTO gcDTO);

    void delGiftCertificate(int id);
}
