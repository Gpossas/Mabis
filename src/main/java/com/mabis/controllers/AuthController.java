package com.mabis.controllers;

import com.mabis.domain.user.*;
import com.mabis.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController
{
    private final AuthService auth_service;

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> register(@Valid @RequestBody RegisterUserDTO dto)
    {
        return new ResponseEntity<>(auth_service.register(dto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO credentials)
    {
        return ResponseEntity.ok(auth_service.login(credentials));
    }
}

