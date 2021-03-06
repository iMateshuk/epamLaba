package com.epam.esm.service.security;

import com.epam.esm.dao.entity.RoleEntity;
import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.service.config.ServiceProperties;
import com.epam.esm.service.dto.ErrorDTO;
import com.epam.esm.service.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class JwtProvider {
  private final ServiceProperties properties;

  public String generateToken(UserEntity userEntity) {
    Date date = Date.from(LocalDate.now()
        .plusDays(properties.getJwtDuration())
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
    );
    return Jwts.builder()
        .setId(userEntity.getId().toString())
        .setSubject(userEntity.getLogin())
        .setExpiration(date)
        .claim(Role.ROLES, userEntity.getRoles().stream()
            .map(RoleEntity::getName)
            .collect(Collectors.toList())
        )
        .signWith(SignatureAlgorithm.HS512, properties.getJwtSecret())
        .compact();
  }

  public Claims extractClaims(String token) {
    Claims claims;
    try {
      claims = extractAllClaims(token).getBody();
    } catch (ExpiredJwtException expEx) {
      throw new UnauthorizedException(new ErrorDTO("jwt.token.expired", token), 701);
    } catch (UnsupportedJwtException unsEx) {
      throw new UnauthorizedException(new ErrorDTO("jwt.unsupported", token), 702);
    } catch (MalformedJwtException mjEx) {
      throw new UnauthorizedException(new ErrorDTO("jwt.malformed", token), 703);
    } catch (SignatureException sEx) {
      throw new UnauthorizedException(new ErrorDTO("jwt.invalid.signature", token), 704);
    } catch (IllegalArgumentException e) {
      throw new UnauthorizedException(new ErrorDTO("jwt.invalid.token", token), 705);
    }
    return claims;
  }

  @SuppressWarnings("unchecked")
  public List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
    return Optional.ofNullable(claims.get(Role.ROLES))
        .map(claim -> (List<String>) claim)
        .orElseGet(Collections::emptyList)
        .stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  private Jws<Claims> extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(properties.getJwtSecret()).parseClaimsJws(token);
  }
}
