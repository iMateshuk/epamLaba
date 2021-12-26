package com.epam.esm.service.validation;

import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.service.dto.AuthRequest;
import com.epam.esm.service.dto.ErrorDTO;
import com.epam.esm.service.exception.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@Validated
public class Validator {
  private PasswordEncoder passwordEncoder;

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
      throw new ValidationException(errors, 1);
    }
  }

  public void validateLogin(AuthRequest authRequest, UserEntity userEntity) {
    List<ErrorDTO> errors = new ArrayList<>();

    if (userEntity == null || !passwordEncoder.matches(authRequest.getPassword(), userEntity.getPassword())) {
      errors.add(new ErrorDTO("user.login.error"));
    }
    if (!errors.isEmpty()) {
      throw new ValidationException(errors, 2);
    }
  }
}
