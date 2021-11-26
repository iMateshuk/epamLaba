package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertificateDTO;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService {
  GiftCertificateDTO insertCertificate(GiftCertificateDTO gcDTO);

  List<GiftCertificateDTO> findAllCertificates();

  GiftCertificateDTO findCertificate(int id);

  List<GiftCertificateDTO> findAllCertificates(Map<String, String> allParameters);

  GiftCertificateDTO updateCertificate(GiftCertificateDTO giftCertificateDTO);

  void deleteCertificate(int id);
}
