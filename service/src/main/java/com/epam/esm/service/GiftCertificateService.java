package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.page.Page;
import com.epam.esm.service.page.PageParam;

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
   * @return PAageDTO
   *
   * The method can throw ServiceException extends RuntimeException
   */
  GiftCertificateDTO findById(Integer id);

  /**
   *
   * @param allParameters
   * @param pageParam
   * @return
   */
  Page<GiftCertificateDTO> findAll(Map<String, String> allParameters, PageParam pageParam);

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
