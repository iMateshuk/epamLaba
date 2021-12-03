package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.ErrorDTO;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.exception.ServiceConflictException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ServiceValidationException;
import com.epam.esm.service.util.RequestedParameter;
import com.epam.esm.service.util.ServiceConvertor;
import com.epam.esm.service.util.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
@AllArgsConstructor
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
  private final GiftCertificateDAO certificateDAO;
  private final Validator validator;
  private final ServiceConvertor convertor;

  /**
   * @param certificateDTO DTO object
   * @return GiftCertificateDTO
   * <p>
   * The method can throw ServiceConflictException extends RuntimeException<p>
   */
  @Transactional
  @Override
  public GiftCertificateDTO insert(GiftCertificateDTO certificateDTO) {
    String certificateName = certificateDTO.getName();
    validator.matchField(certificateName, certificateDTO.getDescription());
    if (certificateDAO.isExistByName(certificateName)) {
      throw new ServiceConflictException(new ErrorDTO("certificate.name.create.error", certificateName), 101);
    }
    if (certificateDTO.getTags() == null) {
      certificateDTO.setTags(new ArrayList<>());
    }
    return convertor.toTarget(certificateDAO
        .insert(convertor.toTarget(certificateDTO, GiftCertificateEntity.class)), GiftCertificateDTO.class);
  }

  /**
   * @return List of GiftCertificateDTO
   */
  @Transactional
  @Override
  public List<GiftCertificateDTO> findAll() {
    return convertor.toTarget(certificateDAO.findAll(), GiftCertificateDTO.class);
  }

  /**
   * @param id positive int
   * @return GiftCertificateDTO
   * <p>
   * The method can throw ServiceException extends RuntimeException
   */
  @Transactional
  @Override
  public GiftCertificateDTO findById(Integer id) {
    if (!certificateDAO.isExistById(id)) {
      throw new ServiceException(new ErrorDTO("certificate.search.error", id), 103);
    }
    return convertor.toTarget(certificateDAO.findById(id), GiftCertificateDTO.class);
  }

  /**
   * @param allParameters Map of parameters
   * @return List of GiftCertificateDTO
   * <p>
   * The method can throw ServiceValidationException or ServiceException extends RuntimeException
   */
  @Transactional
  @Override
  public List<GiftCertificateDTO> findAllWithParam(Map<String, String> allParameters) {
    Map<String, String> parameters = createMapParameter(allParameters);
    if (parameters.isEmpty()) {
      throw new ServiceValidationException(new ErrorDTO("certificate.parameters.error"), 105);
    }
    return convertor.toTarget(certificateDAO.findAllWithParam(parameters), GiftCertificateDTO.class);
  }

  /**
   * @param certificateDTO DTO object
   * @return GiftCertificateDTO
   * <p>
   * The method can throw ServiceException extends RuntimeException<p>
   * The method uses a transaction
   */
  @Transactional
  @Override
  public GiftCertificateDTO update(GiftCertificateDTO certificateDTO) {
    int id = certificateDTO.getId();
    if (!certificateDAO.isExistById(id)) {
      throw new ServiceException(new ErrorDTO("certificate.search.error", id), 106);
    }

    String certificateName = certificateDTO.getName();
    if (certificateName != null) {
      validator.matchField(certificateName);
    }

    String certificateDescription = certificateDTO.getDescription();
    if (certificateDescription != null) {
      validator.matchField(certificateDescription);
    }
    return convertor.toTarget(certificateDAO
        .update(convertor.toTarget(certificateDTO, GiftCertificateEntity.class)), GiftCertificateDTO.class);
  }

  /**
   * @param id positive int
   *           <p>
   *           The method can throw ServiceException extends RuntimeException
   */
  @Transactional
  @Override
  public void deleteById(Integer id) {
    if (!certificateDAO.isExistById(id)) {
      throw new ServiceException(new ErrorDTO("certificate.delete.error", id), 104);
    }
    certificateDAO.deleteById(id);
  }

  private Map<String, String> createMapParameter(Map<String, String> allRequestParams) {
    return Arrays.stream(RequestedParameter.values())
        .filter((parameter) -> allRequestParams.get(parameter.getParameterKey()) != null)
        .collect(Collectors.toMap(RequestedParameter::toString,
            (parameter) -> (allRequestParams.get(parameter.getParameterKey()))));
  }
}
