package com.epam.esm.config;

import com.epam.esm.exception.FilterChainExceptionHandler;
import com.epam.esm.filter.JwtFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true,
    prePostEnabled = true
)
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  private final JwtFilter jwtFilter;
  private FilterChainExceptionHandler filterChainExceptionHandler;

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .addFilterBefore(filterChainExceptionHandler, LogoutFilter.class)
        .httpBasic().disable()
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers(
            HttpMethod.GET,
            "/certificates/**"
        ).permitAll()
        .antMatchers(
            HttpMethod.POST,
            "/signup",
            "/login"
        ).permitAll()
        .antMatchers(
            "/users/{id}/**"
        ).access("@guard.checkUserId(authentication, #id) or @guard.isAdmin(authentication)")
        .antMatchers(
            HttpMethod.GET,
            "/**"
        ).hasRole("USER")
        .antMatchers(
            HttpMethod.POST,
            "/orders"
        ).hasRole("USER")
        .antMatchers(
            "/**"
        ).hasRole("ADMIN")
        .and()
        .headers().cacheControl().disable()
        .and()
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
    ;
  }
}
