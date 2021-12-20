package com.epam.esm.filter;

import com.epam.esm.config.Role;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*@Component
@AllArgsConstructor
@Order(2)*/
public class PathUserFilter extends OncePerRequestFilter {
  private static final String PATH_MATCH_REG_EX = ".*/users/%s(/.*|)";
  private static final String ROLE_ADMIN_REG_EX = ".*" + Role.ROLE_ADMIN + ".*";

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String userId = (String) auth.getCredentials();
    if (!(
        request.getRequestURI().matches(String.format(PATH_MATCH_REG_EX, userId)) ||
            auth.getAuthorities().toString().matches(ROLE_ADMIN_REG_EX)
    )) {
      throw new AccessDeniedException("access.denied");
    }
    filterChain.doFilter(request, response);
  }
}
