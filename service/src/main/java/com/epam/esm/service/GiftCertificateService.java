package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertificateDTO;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService {
  GiftCertificateDTO insert(GiftCertificateDTO gcDTO);

  List<GiftCertificateDTO> findAll();

  GiftCertificateDTO findById(int id);

  List<GiftCertificateDTO> findAllWithParam(Map<String, String> allParameters);

  GiftCertificateDTO update(GiftCertificateDTO giftCertificateDTO);

  void deleteById(int id);
}
