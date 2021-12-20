package com.epam.esm.filter;

import com.epam.esm.service.security.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Log
@AllArgsConstructor
@Order(1)
public class JwtFilter extends OncePerRequestFilter {
  private static final String AUTHORIZATION = "Authorization";
  private static final String BEARER = "Bearer ";
  private static final int SUBSTRING = 7;

  private final JwtProvider jwtProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String token = null;
    String authorizationHeader = request.getHeader(AUTHORIZATION);
    if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
      token = authorizationHeader.substring(SUBSTRING);
    }

    if (token != null && jwtProvider.validateToken(token)) {
      UsernamePasswordAuthenticationToken auth =
          new UsernamePasswordAuthenticationToken(
              jwtProvider.getLogin(token), jwtProvider.getUserId(token), jwtProvider.getAuthorities(token)
          );
      SecurityContextHolder.getContext().setAuthentication(auth);
    }
    filterChain.doFilter(request, response);
  }
}
