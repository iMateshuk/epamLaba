package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.dto.PageDTO;
import com.epam.esm.service.dto.PageParamDTO;

import java.util.Map;

public interface GiftCertificateService {

  /**
   * @param certificateDTO DTO object
   * @return GiftCertificateDTO
   * <p>
   * The method can throw ServiceConflictException extends RuntimeException<p>
   */
  GiftCertificateDTO insert(GiftCertificateDTO certificateDTO);

  /**
   *
   * @param id
   * @param pageParamDTO
   * @return PAageDTO
   *
   * The method can throw ServiceException extends RuntimeException
   */
  PageDTO<GiftCertificateDTO> findById(Integer id, PageParamDTO pageParamDTO);

  /**
   *
   * @param allParameters
   * @param pageParamDTO
   * @return
   */
  PageDTO<GiftCertificateDTO> findAll(Map<String, String> allParameters, PageParamDTO pageParamDTO);

  /**
   *
   * @param giftCertificateDTO
   * @return
   */
  GiftCertificateDTO update(GiftCertificateDTO giftCertificateDTO);

  /**
   *
   * @param id
   */
  void deleteById(Integer id);
}
