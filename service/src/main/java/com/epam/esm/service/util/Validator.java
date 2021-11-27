package com.epam.esm.service.util;

import com.epam.esm.service.dto.ErrorDto;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Validator {
  private static final int MIN_VALUE = 0;
  private static final int MIN_LEN_NAME = 3;
  private static final String RE_MATCH = "[\\w+( )?]+";

  public void checkCreationCertificate(GiftCertificateDTO giftCertificateDTO) {
    List<ErrorDto> errors = new ArrayList<>();

    String certificateName = giftCertificateDTO.getName();
    if (certificateName == null || certificateName.length() < MIN_LEN_NAME) {
      errors.add(new ErrorDto("certificate.name.min.length.error", MIN_LEN_NAME));
    }

    String description = giftCertificateDTO.getDescription();
    if (description == null || description.length() < MIN_LEN_NAME) {
      errors.add(new ErrorDto("certificate.description.min.length.error", MIN_LEN_NAME));
    }

    if (giftCertificateDTO.getPrice() <= MIN_VALUE) {
      errors.add(new ErrorDto("certificate.price.min.error", MIN_VALUE));
    }

    if (giftCertificateDTO.getDuration() <= MIN_VALUE) {
      errors.add(new ErrorDto("certificate.duration.min.error", MIN_VALUE));
    }

    /*if (giftCertificateDTO.getTags().isEmpty()) {
      errors.add(new ErrorDto("certificate.tags.empty.error", MIN_VALUE));
    }*/

    if (!errors.isEmpty()) {
      throw new ValidationException(errors, 1);
    }
  }

  public void checkUpdateCertificate(GiftCertificateDTO giftCertificateDTO) {
    List<ErrorDto> errors = new ArrayList<>();
    Integer id = giftCertificateDTO.getId();
    if (id != null && id <= MIN_VALUE) {
      errors.add(new ErrorDto("certificate.id.min.error", MIN_VALUE));
    }

    String certificateName = giftCertificateDTO.getName();
    if (certificateName != null && certificateName.length() < MIN_LEN_NAME) {
      errors.add(new ErrorDto("certificate.name.min.length.error", MIN_LEN_NAME));
    }

    String description = giftCertificateDTO.getDescription();
    if (description != null && description.length() < MIN_LEN_NAME) {
      errors.add(new ErrorDto("certificate.description.min.length.error", MIN_LEN_NAME));
    }
    Float price = giftCertificateDTO.getPrice();
    if (price != null && price < MIN_VALUE) {
      errors.add(new ErrorDto("certificate.price.min.error", MIN_VALUE));
    }
    Integer duration = giftCertificateDTO.getDuration();
    if (duration != null && duration < MIN_VALUE) {
      errors.add(new ErrorDto("certificate.duration.min.error", MIN_VALUE));
    }

    if (!errors.isEmpty()) {
      throw new ValidationException(errors, 1);
    }
  }

  public void checkTagName(String tagName) {
    List<ErrorDto> errors = new ArrayList<>();

    if (tagName == null || tagName.length() < MIN_LEN_NAME) {
      errors.add(new ErrorDto("tag.name.min.length.error", MIN_LEN_NAME));
    }

    if (!errors.isEmpty()) {
      throw new ValidationException(errors, 2);
    }
  }

  public void matchField(String... fields) {
    List<ErrorDto> errors = new ArrayList<>();

    Stream.of(fields).forEach((field) -> {
      if (!field.matches(RE_MATCH)) {
        errors.add(new ErrorDto("field.match.error", field));
      }
    });

    if (!errors.isEmpty()) {
      throw new ValidationException(errors, 3);
    }
  }

  public void checkId(int id) {
    List<ErrorDto> errors = new ArrayList<>();

    if (id <= MIN_VALUE) {
      errors.add(new ErrorDto("id.min.error", MIN_VALUE));
    }

    if (!errors.isEmpty()) {
      throw new ValidationException(errors, 3);
    }
  }
}
