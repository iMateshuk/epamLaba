package com.epam.esm.service.util;

import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.service.dto.AuthRequest;
import com.epam.esm.service.dto.ErrorDTO;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.exception.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class Validator {
  private PasswordEncoder passwordEncoder;

  private static final int MIN_VALUE = 0;
  private static final int MIN_LEN_NAME = 3;
  private static final String RE_MATCH = "[\\w+( )?]+";

  public void validateCertificateForUpdate(GiftCertificateDTO giftCertificateDTO) {
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

  public void validateEntitiesOfPurchaseDto(GiftCertificateEntity certificateEntity, Integer certId,
                                            UserEntity userEntity, Integer userId) {
    List<ErrorDTO> errors = new ArrayList<>();

    if (certificateEntity == null) {
      errors.add(new ErrorDTO("purchase.certid.notfound", certId));
    }
    if (userEntity == null) {
      errors.add(new ErrorDTO("purchase.userid.notfound", userId));
    }
    if (!errors.isEmpty()) {
      throw new ValidationException(errors, 4);
    }
  }

  public void validateLogin(AuthRequest authRequest, UserEntity userEntity) {
    List<ErrorDTO> errors = new ArrayList<>();

    if (userEntity == null || !passwordEncoder.matches(authRequest.getPassword(), userEntity.getPassword())) {
      errors.add(new ErrorDTO("user.login.error"));
    }
    if (!errors.isEmpty()) {
      throw new ValidationException(errors, 5);
    }
  }
}
