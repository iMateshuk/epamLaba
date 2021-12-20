package com.epam.esm.service.security;

import com.epam.esm.dao.entity.RoleEntity;
import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.service.config.ServiceProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
@Log
public class JwtProvider {
  private final ServiceProperties serviceProperties;

  private final static String EMPTY = "";
  private final static String ROLES = "roles";

  public String generateToken(UserEntity userEntity) {
    Date date = Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
    return Jwts.builder()
        .setId(userEntity.getId() + EMPTY)
        .setSubject(userEntity.getLogin())
        .setExpiration(date)
        .claim(ROLES, userEntity.getRoles().stream()
            .map(RoleEntity::getName)
            .collect(Collectors.toList())
        )
        .signWith(SignatureAlgorithm.HS512, serviceProperties.getJwtSecret())
        .compact();
  }

  public boolean validateToken(String token) {
    boolean valid = false;
    try {
      extractAllClaims(token);
      valid = true;
    } catch (ExpiredJwtException expEx) {
      log.severe("Token expired");
    } catch (UnsupportedJwtException unsEx) {
      log.severe("Unsupported jwt");
    } catch (MalformedJwtException mjEx) {
      log.severe("Malformed jwt");
    } catch (SignatureException sEx) {
      log.severe("Invalid signature");
    } catch (Exception e) {
      log.severe("invalid token");
    }
    return valid;
  }

  public String getLogin(String token) {
    return extractAllClaims(token).getBody().getSubject();
  }

  public String getUserId(String token) {
    return extractAllClaims(token).getBody().getId();
  }

  @SuppressWarnings("unchecked")
  public List<SimpleGrantedAuthority> getAuthorities(String token) {
    List<String> roles = (List<String>) extractAllClaims(token).getBody().get(ROLES);
    return roles.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  private Jws<Claims> extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(serviceProperties.getJwtSecret()).parseClaimsJws(token);
  }
}
