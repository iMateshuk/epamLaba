package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.ErrorDTO;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.exception.ServiceConflictException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.page.Page;
import com.epam.esm.service.page.PageParam;
import com.epam.esm.service.util.Mapper;
import com.epam.esm.service.util.RequestedParameter;
import com.epam.esm.service.util.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
  private final Mapper mapper;

  @Transactional
  @Override
  public GiftCertificateDTO insert(GiftCertificateDTO certificateDTO) {
    String certificateName = certificateDTO.getName();
    if (certificateDAO.isExistByName(certificateName)) {
      throw new ServiceConflictException(new ErrorDTO("certificate.name.create.error", certificateName), 101);
    }
    List<TagDTO> tags = certificateDTO.getTags();
    if (tags != null && !tags.isEmpty()) {
      tags.forEach(validator::validateTagDTO);
    }
    if (tags == null) {
      certificateDTO.setTags(new ArrayList<>());
    }
    return mapper.toTarget(
        certificateDAO.insert(mapper.toTarget(certificateDTO, GiftCertificateEntity.class)),
        GiftCertificateDTO.class
    );
  }

  @Override
  public GiftCertificateDTO findById(Integer id) {
    GiftCertificateEntity certificateEntity = certificateDAO.findById(id);
    if (certificateEntity == null) {
      throw new ServiceException(new ErrorDTO("certificate.search.error", id), 103);
    }
    return mapper.toTarget(certificateEntity, GiftCertificateDTO.class);
  }

  @Override
  public Page<GiftCertificateDTO> findAll(Map<String, String> allParameters, PageParam pageParam) {
    Map<String, String> parameters = createMapParameter(allParameters);
    int pageNumber = pageParam.getPageNumber();
    int pageSize = pageParam.getPageSize();

    List<GiftCertificateEntity> certificates =
        certificateDAO.findAll(parameters, pageNumber, pageSize);
    Long count = certificateDAO.count(parameters);

    return Page.<GiftCertificateDTO>builder()
        .pageSize(pageSize)
        .pageNumber(pageNumber)
        .totalElements(count)
        .lastPage(count / pageParam.getPageSize())
        .list(mapper.toTarget(certificates, GiftCertificateDTO.class))
        .build();
  }

  @Transactional
  @Override
  public GiftCertificateDTO update(GiftCertificateDTO certificateDTO) {
    int id = certificateDTO.getId();
    if (!certificateDAO.isExistById(id)) {
      throw new ServiceException(new ErrorDTO("certificate.search.error", id), 106);
    }
    List<String> fields = new ArrayList<>();
    fields.add(certificateDTO.getName());
    fields.add(certificateDTO.getDescription());

    validator.matchField(fields.stream().filter(Objects::nonNull).toArray(String[]::new));
    return mapper.toTarget(
        certificateDAO.update(mapper.toTarget(certificateDTO, GiftCertificateEntity.class)),
        GiftCertificateDTO.class
    );
  }

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
