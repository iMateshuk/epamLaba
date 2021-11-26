package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.ErrorDto;
import com.epam.esm.service.dto.GiftCertificateConverter;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.exception.ServiceConflictException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ServiceValidationException;
import com.epam.esm.service.util.RequestedParameter;
import com.epam.esm.service.util.Validator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service Gift-Certificate
 * Use for business logic of APP
 *
 * @author Ivan Matsiashuk
 * @version 1.0
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
  private final GiftCertificateDAO giftCertificateDAO;
  private final Validator validator;

  public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO, Validator validator) {
    this.giftCertificateDAO = giftCertificateDAO;
    this.validator = validator;
  }

  /**
   * @param requestGiftCertificateDTO DTO object
   * @return GiftCertificateDTO
   * <p>
   * The method can throw ServiceConflictException extends RuntimeException<p>
   */
  @Transactional
  @Override
  public GiftCertificateDTO insertCertificate(GiftCertificateDTO requestGiftCertificateDTO) {
    String certificateName = requestGiftCertificateDTO.getName();
    validator.matchField(certificateName, requestGiftCertificateDTO.getDescription());
    if (giftCertificateDAO.isExistCertificate(certificateName)) {
      throw new ServiceConflictException(new ErrorDto("certificate.name.create.error", certificateName), 101);
    }
    return GiftCertificateConverter
        .toDto(giftCertificateDAO.insertCertificate(GiftCertificateConverter.toEntity(requestGiftCertificateDTO)));
  }

  /**
   * @return List of GiftCertificateDTO
   */
  @Transactional
  @Override
  public List<GiftCertificateDTO> findAllCertificates() {
    return GiftCertificateConverter
        .toDto(giftCertificateDAO.findAllCertificates());
  }

  /**
   * @param id positive int
   * @return GiftCertificateDTO
   * <p>
   * The method can throw ServiceException extends RuntimeException
   */
  @Transactional
  @Override
  public GiftCertificateDTO findCertificate(int id) {
    if (!giftCertificateDAO.isExistCertificate(id)) {
      throw new ServiceException(new ErrorDto("certificate.search.error", id), 103);
    }
    return GiftCertificateConverter
        .toDto(giftCertificateDAO.findCertificate(id));
  }

  /**
   * @param allParameters Map of parameters
   * @return List of GiftCertificateDTO
   * <p>
   * The method can throw ServiceValidationException or ServiceException extends RuntimeException
   */
  @Transactional
  @Override
  public List<GiftCertificateDTO> findAllCertificates(Map<String, String> allParameters) {
    Map<String, String> parameters = createMapParameter(allParameters);
    if (parameters.isEmpty()) {
      throw new ServiceValidationException(new ErrorDto("certificate.parameters.error"), 105);
    }

    List<GiftCertificateDTO> createdGiftCertificateDTOs = GiftCertificateConverter
        .toDto(giftCertificateDAO.findAllCertificates(parameters));
    if (createdGiftCertificateDTOs.isEmpty()) {
      throw new ServiceException(new ErrorDto("dao.empty.result.error"), 115);
    }

    return createdGiftCertificateDTOs;
  }

  /**
   * @param requestGiftCertificateDTO DTO object
   * @return GiftCertificateDTO
   * <p>
   * The method can throw ServiceException extends RuntimeException<p>
   * The method uses a transaction
   */
  @Transactional
  @Override
  public GiftCertificateDTO updateCertificate(GiftCertificateDTO requestGiftCertificateDTO) {
    int id = requestGiftCertificateDTO.getId();
    if (!giftCertificateDAO.isExistCertificate(id)) {
      throw new ServiceException(new ErrorDto("certificate.search.error", id), 106);
    }

    String certificateName = requestGiftCertificateDTO.getName();
    if (certificateName != null) {
      validator.matchField(certificateName);
    }

    String certificateDescription = requestGiftCertificateDTO.getDescription();
    if (certificateDescription != null) {
      validator.matchField(certificateDescription);
    }

    return GiftCertificateConverter.toDto(giftCertificateDAO
        .updateCertificate(GiftCertificateConverter.toEntity(requestGiftCertificateDTO)));
  }

  /**
   * @param id positive int
   * <p>
   * The method can throw ServiceException extends RuntimeException
   */
  @Transactional
  @Override
  public void deleteCertificate(int id) {
    if (!giftCertificateDAO.isExistCertificate(id)) {
      throw new ServiceException(new ErrorDto("certificate.delete.error", id), 104);
    }
    giftCertificateDAO.deleteCertificate(id);
  }

  private Map<String, String> createMapParameter(Map<String, String> allRequestParams) {
    return Arrays.stream(RequestedParameter.class.getEnumConstants())
        .filter((parameter) -> allRequestParams.get(parameter.getParameterKey()) != null)
        .collect(Collectors.toMap(RequestedParameter::toString, (parameter) -> (allRequestParams.get(parameter.getParameterKey()))));
  }
}
