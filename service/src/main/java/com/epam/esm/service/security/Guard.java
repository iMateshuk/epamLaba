package com.epam.esm.service.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class Guard {
  private static final String ROLE_ADMIN_REG_EX = ".*" + Role.ROLE_ADMIN + ".*";

  public boolean checkUserId(Authentication auth, String id) {
    return id.equals(auth.getCredentials().toString());
  }

  public boolean checkUserRole(Authentication auth) {
    return auth.getAuthorities().toString().matches(ROLE_ADMIN_REG_EX);
  }
}
