package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.ErrorDto;
import com.epam.esm.service.dto.GiftCertificateConverter;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.dto.TagConverter;
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
 *  Use for business logic of APP
 *
 *  @author Ivan Matsiashuk
 *  @version 1.0
 */
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

  /**
   *
   * @param requestGiftCertificateDTO DTO object
   * @return GiftCertificateDTO
   *
   * The method can throw ValidationException extends RuntimeException
   * The method uses a transaction
   */
  @Transactional
  @Override
  public GiftCertificateDTO createGiftCertificate(GiftCertificateDTO requestGiftCertificateDTO) {
    validator.matchField(requestGiftCertificateDTO.getName(), requestGiftCertificateDTO.getDescription());
    String certificateName = requestGiftCertificateDTO.getName();
    if(giftCertificateDAO.isExistGiftCertificate(certificateName)){
      throw new ServiceException(new ErrorDto("certificate.name.create.error", certificateName), 101);
    }

    GiftCertificateDTO createdGiftCertificateDTO = GiftCertificateConverter
        .toDto(giftCertificateDAO.createGiftCertificate(GiftCertificateConverter.toEntity(requestGiftCertificateDTO)));

    checkTagNameAndBundleWithGiftCertificate(requestGiftCertificateDTO, createdGiftCertificateDTO.getId());
    addTagToDTO(createdGiftCertificateDTO);
    return createdGiftCertificateDTO;
  }

  /**
   *
   * @return List of GiftCertificateDTO
   */
  @Override
  public List<GiftCertificateDTO> searchGiftCertificates() {
    List<GiftCertificateDTO> createdGiftCertificateDTOs = GiftCertificateConverter
        .toDto(giftCertificateDAO.getGiftCertificates());

    addTagToDTO(createdGiftCertificateDTOs);
    return createdGiftCertificateDTOs;
  }

  /**
   *
   * @param id positive int
   * @return GiftCertificateDTO
   */
  @Override
  public GiftCertificateDTO searchGiftCertificate(int id) {
    if (!giftCertificateDAO.isExistGiftCertificate(id)) {
      throw new ServiceException(new ErrorDto("certificate.search.error", id), 103);
    }
    GiftCertificateDTO createdGiftCertificateDTO = GiftCertificateConverter
        .toDto(giftCertificateDAO.getGiftCertificate(id));

    addTagToDTO(createdGiftCertificateDTO);
    return createdGiftCertificateDTO;
  }

  /**
   *
   * @param allParameters Map<String, String>
   * @return List of GiftCertificateDTO
   *
   * The method can throw ServiceException extends RuntimeException
   */
  @Override
  public List<GiftCertificateDTO> searchGiftCertificates(Map<String, String> allParameters) {
    Map<String, String> parameters = createMapParameter(allParameters);
    if (parameters.isEmpty()) {
      throw new ServiceValidationException(new ErrorDto("certificate.parameters.error"), 105);
    }

    List<GiftCertificateDTO> createdGiftCertificateDTOs = GiftCertificateConverter
        .toDto(giftCertificateDAO.getGiftCertificates(parameters));
    if (createdGiftCertificateDTOs.isEmpty()) {
      throw new ServiceValidationException(new ErrorDto("dao.empty.result.error"), 115);
    }

    addTagToDTO(createdGiftCertificateDTOs);
    return createdGiftCertificateDTOs;
  }

  /**
   *
   * @param requestGiftCertificateDTO DTO object
   * @return GiftCertificateDTO
   *
   * The method can throw ValidationException extends RuntimeException
   * The method uses a transaction
   */
  @Transactional
  @Override
  public GiftCertificateDTO patchGiftCertificate(GiftCertificateDTO requestGiftCertificateDTO) {
    int id = requestGiftCertificateDTO.getId();
    if (!giftCertificateDAO.isExistGiftCertificate(id)) {
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

    GiftCertificateDTO updatedGiftCertificateDTO = updateGiftCertificate(requestGiftCertificateDTO);
    if (!requestGiftCertificateDTO.getTags().isEmpty()) {
      giftCertificateDAO.delGiftCertificateAndTagBundle(id);
      checkTagNameAndBundleWithGiftCertificate(requestGiftCertificateDTO, id);
    }

    addTagToDTO(updatedGiftCertificateDTO);
    return updatedGiftCertificateDTO;
  }

  /**
   *
   * @param id positive int
   */
  @Override
  public void delGiftCertificate(int id) {
    if (!giftCertificateDAO.isExistGiftCertificate(id)) {
      throw new ServiceException(new ErrorDto("certificate.delete.error", id), 104);
    }
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
        .stream().filter((tagEntity) -> tagEntity.getName() != null)
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
