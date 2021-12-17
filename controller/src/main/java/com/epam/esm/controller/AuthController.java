package com.epam.esm.controller;

import com.epam.esm.service.AuthService;
import com.epam.esm.service.dto.AuthRequest;
import com.epam.esm.service.dto.AuthResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping
public class AuthController {
  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<AuthResponse> save(@Valid @RequestBody AuthRequest authRequest) {
    return new ResponseEntity<>(authService.save(authRequest), HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
    return new ResponseEntity<>(authService.login(authRequest), HttpStatus.OK);
  }
}
