package com.epam.esm.service.security;

import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.service.config.ServiceProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@AllArgsConstructor
@Component
@Log
public class JwtProvider {
  private final ServiceProperties serviceProperties;

  public String generateToken(UserEntity userEntity) {
    Date date = Date.from(LocalDate.now().plusDays(10).atStartOfDay(ZoneId.systemDefault()).toInstant());
    return Jwts.builder()
        .setId(userEntity.getId() + "")
        .setSubject(userEntity.getLogin())
        .setExpiration(date)
        .signWith(SignatureAlgorithm.HS512, serviceProperties.getJwtSecret())
        .compact();
  }

  public boolean validateToken(String token) {
    boolean valid = false;
    try {
      Jwts.parser().setSigningKey(serviceProperties.getJwtSecret()).parseClaimsJws(token);
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

  public String getLoginFromToken(String token) {
    Claims claims = Jwts.parser().setSigningKey(serviceProperties.getJwtSecret()).parseClaimsJws(token).getBody();
    return claims.getSubject();
  }
}
