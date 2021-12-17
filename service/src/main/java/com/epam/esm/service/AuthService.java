package com.epam.esm.service;

import com.epam.esm.service.dto.AuthRequest;
import com.epam.esm.service.dto.AuthResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {

  /**
   *
   * @param authRequest
   * @return
   */
  AuthResponse save(AuthRequest authRequest);

  /**
   *
   * @param authRequest
   * @return
   */
  AuthResponse login(AuthRequest authRequest);

  /**
   *
   * @param login
   * @return
   */
  UserDetails findByLogin(String login);
}
