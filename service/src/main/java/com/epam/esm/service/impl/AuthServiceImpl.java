package com.epam.esm.service.impl;

import com.epam.esm.dao.RoleDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dao.entity.RoleEntity;
import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.service.AuthService;
import com.epam.esm.service.dto.AuthRequest;
import com.epam.esm.service.dto.AuthResponse;
import com.epam.esm.service.dto.ErrorDTO;
import com.epam.esm.service.exception.ServiceConflictException;
import com.epam.esm.service.security.JwtProvider;
import com.epam.esm.service.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final UserDAO userDAO;
  private final RoleDAO roleDAO;
  private final Validator validator;
  private final JwtProvider jwtProvider;
  private final PasswordEncoder passwordEncoder;

  private final static String ROLE_USER = "ROLE_USER";
  private final static String EMPTY = "";

  @Transactional
  @Override
  public AuthResponse save(AuthRequest authRequest) {
    String login = authRequest.getLogin();
    if (userDAO.isUserExist(login)) {
      throw new ServiceConflictException(new ErrorDTO("user.login.search.error", login), 501);
    }
    UserEntity userEntity =
        userDAO.save(UserEntity.builder()
            .login(login)
            .password(passwordEncoder.encode(authRequest.getPassword()))
            .roles(List.of(roleDAO.findByName(ROLE_USER)))
            .orders(new ArrayList<>())
            .build()
        );
    return buildAuthResponse(userEntity, EMPTY);
  }

  @Override
  public AuthResponse login(AuthRequest authRequest) {
    UserEntity userEntity = userDAO.findByLogin(authRequest.getLogin());
    validator.validateLogin(authRequest, userEntity);
    return buildAuthResponse(userEntity, jwtProvider.generateToken(userEntity));
  }

  private AuthResponse buildAuthResponse(UserEntity userEntity, String token) {
    return AuthResponse.builder()
        .id(userEntity.getId())
        .login(userEntity.getLogin())
        .roles(userEntity.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList()))
        .token(token)
        .build();
  }
}
