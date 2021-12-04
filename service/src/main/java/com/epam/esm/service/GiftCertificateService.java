package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.dto.PageDTO;
import com.epam.esm.service.dto.PageParamDTO;

import java.util.Map;

public interface GiftCertificateService {
  GiftCertificateDTO insert(GiftCertificateDTO gcDTO);

  PageDTO<GiftCertificateDTO> findAll(PageParamDTO pageParamDTO);

  PageDTO<GiftCertificateDTO> findById(Integer id, PageParamDTO pageParamDTO);

  PageDTO<GiftCertificateDTO> findAllWithParam(Map<String, String> allParameters, PageParamDTO pageParamDTO);

  GiftCertificateDTO update(GiftCertificateDTO giftCertificateDTO);

  void deleteById(Integer id);
}
