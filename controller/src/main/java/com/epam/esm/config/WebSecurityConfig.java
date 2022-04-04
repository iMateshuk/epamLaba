package com.epam.esm.config;

import com.epam.esm.filter.FilterChainExceptionHandler;
import com.epam.esm.filter.JwtFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  private final JwtFilter jwtFilter;
  private final FilterChainExceptionHandler filterChainExceptionHandler;
  private final Environment env;

  private static final String DEV_PROFILE = "dev";

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {

    if (Arrays.asList(env.getActiveProfiles()).contains(DEV_PROFILE)) {
      httpSecurity = httpSecurity
          .authorizeRequests()
          .antMatchers("/").permitAll()
          .antMatchers("/dist/react-app.js").permitAll()
          .antMatchers("/dist/react-app.js.map").permitAll()
          .antMatchers("/h2-console/**").permitAll()
          .and()
          .headers().frameOptions().disable()
          .and()
      ;
    }

    httpSecurity
        .addFilterBefore(filterChainExceptionHandler, LogoutFilter.class)
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .httpBasic().disable()
        .csrf().disable()
        .headers().cacheControl().disable()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers(
            "/users/{id}/**"
        ).access("@guard.checkUserId(authentication, #id) or @guard.isAdmin(authentication)")
        .antMatchers(HttpMethod.GET, "/certificates/**").permitAll()
        .antMatchers(HttpMethod.POST, "/signup", "/login").permitAll()
        .antMatchers(HttpMethod.GET, "/**").hasRole("USER")
        .antMatchers(HttpMethod.POST, "/orders").hasRole("USER")
        .antMatchers("/**").hasRole("ADMIN")
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
        .and()
    ;
  }
}
