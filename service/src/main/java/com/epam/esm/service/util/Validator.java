package com.epam.esm.service.util;

import com.epam.esm.service.dto.ErrorDTO;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.dto.PurchaseDTO;
import com.epam.esm.service.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class Validator {
  private static final int MIN_VALUE = 0;
  private static final int MIN_LEN_NAME = 3;
  private static final String RE_MATCH = "[\\w+( )?]+";

  public void checkCreationCertificate(GiftCertificateDTO giftCertificateDTO) {
    List<ErrorDTO> errors = new ArrayList<>();

    String certificateName = giftCertificateDTO.getName();
    if (certificateName == null || certificateName.length() < MIN_LEN_NAME) {
      errors.add(new ErrorDTO("certificate.name.min.length.error", MIN_LEN_NAME));
    }
    String description = giftCertificateDTO.getDescription();
    if (description == null || description.length() < MIN_LEN_NAME) {
      errors.add(new ErrorDTO("certificate.description.min.length.error", MIN_LEN_NAME));
    }
    Float price = giftCertificateDTO.getPrice();
    if (price == null || price <= MIN_VALUE) {
      errors.add(new ErrorDTO("certificate.price.min.error", MIN_VALUE));
    }
    Integer duration = giftCertificateDTO.getDuration();
    if (duration == null || duration <= MIN_VALUE) {
      errors.add(new ErrorDTO("certificate.duration.min.error", MIN_VALUE));
    }
    if (!errors.isEmpty()) {
      throw new ValidationException(errors, 1);
    }
  }

  public void checkUpdateCertificate(GiftCertificateDTO giftCertificateDTO) {
    List<ErrorDTO> errors = new ArrayList<>();

    Integer id = giftCertificateDTO.getId();
    if (id != null && id <= MIN_VALUE) {
      errors.add(new ErrorDTO("certificate.id.min.error", MIN_VALUE));
    }
    String certificateName = giftCertificateDTO.getName();
    if (certificateName != null && certificateName.length() < MIN_LEN_NAME) {
      errors.add(new ErrorDTO("certificate.name.min.length.error", MIN_LEN_NAME));
    }
    String description = giftCertificateDTO.getDescription();
    if (description != null && description.length() < MIN_LEN_NAME) {
      errors.add(new ErrorDTO("certificate.description.min.length.error", MIN_LEN_NAME));
    }
    Float price = giftCertificateDTO.getPrice();
    if (price != null && price < MIN_VALUE) {
      errors.add(new ErrorDTO("certificate.price.min.error", MIN_VALUE));
    }
    Integer duration = giftCertificateDTO.getDuration();
    if (duration != null && duration < MIN_VALUE) {
      errors.add(new ErrorDTO("certificate.duration.min.error", MIN_VALUE));
    }
    if (!errors.isEmpty()) {
      throw new ValidationException(errors, 1);
    }
  }

  public void checkTagName(String tagName) {
    List<ErrorDTO> errors = new ArrayList<>();

    if (tagName == null || tagName.length() < MIN_LEN_NAME) {
      errors.add(new ErrorDTO("tag.name.min.length.error", MIN_LEN_NAME));
    }
    if (!errors.isEmpty()) {
      throw new ValidationException(errors, 2);
    }
  }

  public void matchField(String... fields) {
    List<ErrorDTO> errors = new ArrayList<>();

    Stream.of(fields).forEach((field) -> {
      if (!field.matches(RE_MATCH)) {
        errors.add(new ErrorDTO("field.match.error", field));
      }
    });
    if (!errors.isEmpty()) {
      throw new ValidationException(errors, 3);
    }
  }

  public void checkId(int id) {
    List<ErrorDTO> errors = new ArrayList<>();

    if (id <= MIN_VALUE) {
      errors.add(new ErrorDTO("id.min.error", MIN_VALUE));
    }
    if (!errors.isEmpty()) {
      throw new ValidationException(errors, 3);
    }
  }

  public void checkPurchaseDTO(PurchaseDTO purchaseDTO) {
    List<ErrorDTO> errors = new ArrayList<>();

    Integer userId = purchaseDTO.getUserId();
    if (userId == null || userId <= MIN_VALUE) {
      errors.add(new ErrorDTO("purchase.userid.error", userId));
    }
    Integer certId = purchaseDTO.getCertId();
    if (certId == null || certId <= MIN_VALUE) {
      errors.add(new ErrorDTO("purchase.certid.error", certId));
    }
    if (!errors.isEmpty()) {
      throw new ValidationException(errors, 4);
    }
  }
}
