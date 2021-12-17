package com.epam.esm.filter;

import com.epam.esm.service.AuthService;
import com.epam.esm.service.security.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

@Component
@Log
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
  private static final String AUTHORIZATION = "Authorization";
  private static final String BEARER = "Bearer ";
  private static final int SUBSTRING = 7;

  private final JwtProvider jwtProvider;
  private final AuthService authService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String token = getTokenFromRequest(request);
    if (token != null && jwtProvider.validateToken(token)) {

      String userLogin = jwtProvider.getLoginFromToken(token);
      UserDetails userDetails = authService.findByLogin(userLogin);

      UsernamePasswordAuthenticationToken auth =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(auth);
    }
    filterChain.doFilter(request, response);
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    String token = null;
    String bearer = request.getHeader(AUTHORIZATION);
    if (hasText(bearer) && bearer.startsWith(BEARER)) {
      token = bearer.substring(SUBSTRING);
    }
    return token;
  }
}
