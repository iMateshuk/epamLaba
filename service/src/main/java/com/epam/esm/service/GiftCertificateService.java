package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertificateDTO;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService {
  GiftCertificateDTO createGiftCertificate(GiftCertificateDTO gcDTO);

  List<GiftCertificateDTO> searchGiftCertificates();

  GiftCertificateDTO searchGiftCertificate(int id);

  List<GiftCertificateDTO> searchGiftCertificates(Map<String, String> allParameters);

  GiftCertificateDTO patchGiftCertificate(GiftCertificateDTO giftCertificateDTO);

  void delGiftCertificate(int id);
}
