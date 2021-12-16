package com.epam.esm.config;

import com.epam.esm.service.security.JwtFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  private final JwtFilter jwtFilter;

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
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
            "/users/signup",
            "/users/login"
        ).permitAll()
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
