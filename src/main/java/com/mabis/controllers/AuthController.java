package com.mabis.controllers;

import com.mabis.domain.user.*;
import com.mabis.exceptions.UnmatchPassword;
import com.mabis.exceptions.UserEmailAlreadyInUse;
import com.mabis.repositories.UserRepository;
import com.mabis.services.AuthService;
import com.mabis.services.JWTService;
import com.mabis.services.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController
{
    private final PasswordEncoder password_encoder;
    private final UserRepository user_repository;
    private final JWTService jwt_service;
    private final UserDetailsServiceImpl user_details_service;
    private final AuthService auth_service;

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> register(@Valid @RequestBody RegisterUserDTO dto)
    {
        return new ResponseEntity<>(auth_service.register(dto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO credentials)
    {
        UserDetails user_details = user_details_service.loadUserByUsername(credentials.email());
        if (!password_encoder.matches(credentials.password(), user_details.getPassword()))
        {
            throw new UnmatchPassword();
        }

        String token = jwt_service.generate_token(user_details.get_user());

        return ResponseEntity.ok(LoginResponseDTO.from_user(user_details.get_user(), token));
    }
}

