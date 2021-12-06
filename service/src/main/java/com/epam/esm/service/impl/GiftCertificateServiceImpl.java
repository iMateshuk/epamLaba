package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.page.Page;
import com.epam.esm.dao.page.PageParam;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.ErrorDTO;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.dto.PageDTO;
import com.epam.esm.service.dto.PageParamDTO;
import com.epam.esm.service.exception.ServiceConflictException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.page.PageConvertorDTO;
import com.epam.esm.service.util.RequestedParameter;
import com.epam.esm.service.util.ServiceConvertor;
import com.epam.esm.service.util.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
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
  private final PageConvertorDTO convertorDTO;

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

  @Transactional
  @Override
  public PageDTO<GiftCertificateDTO> findById(Integer id, PageParamDTO pageParamDTO) {
    if (!certificateDAO.isExistById(id)) {
      throw new ServiceException(new ErrorDTO("certificate.search.error", id), 103);
    }
    Page<GiftCertificateEntity> page =
        certificateDAO.findById(id, convertor.toTarget(pageParamDTO, PageParam.class));
    return convertorDTO.toDto(page, GiftCertificateDTO.class);
  }

  @Transactional
  @Override
  public PageDTO<GiftCertificateDTO> findAll(Map<String, String> allParameters, PageParamDTO pageParamDTO) {
    Map<String, String> parameters = createMapParameter(allParameters);
    Page<GiftCertificateEntity> page =
        certificateDAO.findAll(parameters, convertor.toTarget(pageParamDTO, PageParam.class));
    return convertorDTO.toDto(page, GiftCertificateDTO.class);
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
