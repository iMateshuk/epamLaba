package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.ErrorDto;
import com.epam.esm.service.dto.GiftCertificateConverter;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.dto.TagConverter;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.util.RequestedParameter;
import com.epam.esm.service.util.Validator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
  private final GiftCertificateDAO giftCertificateDAO;
  private final TagDAO tagDAO;
  private final Validator validator;

  public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO, TagDAO tagDAO, Validator validator) {
    this.giftCertificateDAO = giftCertificateDAO;
    this.tagDAO = tagDAO;
    this.validator = validator;
  }

  @Transactional
  @Override
  public GiftCertificateDTO createGiftCertificate(GiftCertificateDTO requestGiftCertificateDTO) {
    validator.matchField(requestGiftCertificateDTO.getName(), requestGiftCertificateDTO.getDescription());

    GiftCertificateDTO createdGiftCertificateDTO = GiftCertificateConverter
        .toDto(giftCertificateDAO.createGiftCertificate(GiftCertificateConverter.toEntity(requestGiftCertificateDTO)));

    checkTagNameAndBundleWithGiftCertificate(requestGiftCertificateDTO, createdGiftCertificateDTO.getId());
    addTagToDTO(createdGiftCertificateDTO);
    return createdGiftCertificateDTO;
  }

  @Override
  public List<GiftCertificateDTO> searchGiftCertificates() {
    List<GiftCertificateDTO> createdGiftCertificateDTOs = GiftCertificateConverter
        .toDto(giftCertificateDAO.getGiftCertificates());

    addTagToDTO(createdGiftCertificateDTOs);
    return createdGiftCertificateDTOs;
  }

  @Override
  public GiftCertificateDTO searchGiftCertificate(int id) {
    GiftCertificateDTO createdGiftCertificateDTO = GiftCertificateConverter
        .toDto(giftCertificateDAO.getGiftCertificate(id));

    addTagToDTO(createdGiftCertificateDTO);
    return createdGiftCertificateDTO;
  }

  @Override
  public List<GiftCertificateDTO> searchGiftCertificates(Map<String, String> allParameters) {
    Map<String, String> parameters = createMapParameter(allParameters);
    if (parameters.isEmpty()) {
      throw new ServiceException(new ErrorDto("certificate.parameters.error"), 105);
    }

    List<GiftCertificateDTO> createdGiftCertificateDTOs = GiftCertificateConverter
        .toDto(giftCertificateDAO.getGiftCertificates(parameters));
    if (createdGiftCertificateDTOs.isEmpty()) {
      throw new ServiceException(new ErrorDto("dao.empty.result.error"), 15);
    }

    addTagToDTO(createdGiftCertificateDTOs);
    return createdGiftCertificateDTOs;
  }

  @Override
  public List<GiftCertificateDTO> getGiftCertificates(String tagName) {
    validator.matchField(tagName);

    List<GiftCertificateEntity> listGiftCertificates = giftCertificateDAO.getGiftCertificates(tagName);
    List<GiftCertificateDTO> createdGiftCertificateDTOs = GiftCertificateConverter.toDto(listGiftCertificates);
    if (createdGiftCertificateDTOs.isEmpty()) {
      throw new ServiceException(new ErrorDto("dao.empty.result.error"), 16);
    }

    addTagToDTO(createdGiftCertificateDTOs);
    return createdGiftCertificateDTOs;
  }

  @Transactional
  @Override
  public GiftCertificateDTO updateGiftCertificateWithTags(GiftCertificateDTO requestGiftCertificateDTO) {
    String certificateName = requestGiftCertificateDTO.getName();
    if (certificateName != null) {
      validator.matchField(certificateName);
    }

    String certificateDescription = requestGiftCertificateDTO.getDescription();
    if (certificateDescription != null) {
      validator.matchField(certificateDescription);
    }

    GiftCertificateDTO updatedGiftCertificateDTO = updateGiftCertificate(requestGiftCertificateDTO);
    if (!requestGiftCertificateDTO.getTags().isEmpty()) {
      int id = requestGiftCertificateDTO.getId();
      giftCertificateDAO.delGiftCertificateAndTagBundle(id);
      checkTagNameAndBundleWithGiftCertificate(requestGiftCertificateDTO, id);
    }

    addTagToDTO(updatedGiftCertificateDTO);
    return updatedGiftCertificateDTO;
  }

  @Override
  public void delGiftCertificate(int id) {
    giftCertificateDAO.delGiftCertificate(id);
  }

  private GiftCertificateDTO updateGiftCertificate(GiftCertificateDTO requestGiftCertificateDTO) {
    return GiftCertificateConverter.toDto(giftCertificateDAO
        .updateGiftCertificate(GiftCertificateConverter.toEntity(requestGiftCertificateDTO)));
  }

  private void addTagToDTO(List<GiftCertificateDTO> giftCertificateDTOs) {
    giftCertificateDTOs.forEach(this::addTagToDTO);
  }

  private void addTagToDTO(GiftCertificateDTO giftCertificateDTO) {
    tagDAO.getListTag(giftCertificateDTO.getId())
        .forEach((tagEntity) -> giftCertificateDTO.getTags().add(TagConverter.toDto(tagEntity)));
  }

  private void checkTagNameAndBundleWithGiftCertificate(GiftCertificateDTO requestGiftCertificateDTO, int id) {
    requestGiftCertificateDTO.getTags().forEach((tagDTO) -> {
          String tagName = tagDTO.getName();
          if (!tagDAO.isTagExist(tagName)) {
            tagDAO.createTag(tagName);
          }
          giftCertificateDAO.addGiftCertificateTag(id, tagName);
        }
    );
  }

  private Map<String, String> createMapParameter(Map<String, String> allRequestParams) {
    return Arrays.stream(RequestedParameter.class.getEnumConstants())
        .filter((parameter) -> allRequestParams.get(parameter.getParameterKey()) != null)
        .collect(Collectors.toMap(RequestedParameter::toString, (parameter) -> (allRequestParams.get(parameter.getParameterKey()))));
  }
}
