package com.mabis.services;

import com.mabis.domain.user.LoginResponseDTO;
import com.mabis.domain.user.RegisterUserDTO;
import com.mabis.domain.user.User;
import com.mabis.exceptions.UserEmailAlreadyInUse;
import com.mabis.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService
{
    private final PasswordEncoder password_encoder;
    private final UserRepository user_repository;
    private final JWTService jwt_service;


    public LoginResponseDTO register(RegisterUserDTO dto)
    {
        if (user_repository.findByEmail(dto.email()).isPresent())
        {
            throw new UserEmailAlreadyInUse();
        }

        User user = new User(dto);
        user.setPassword(password_encoder.encode(dto.password()));
        user = user_repository.save(user);

        String token = jwt_service.generate_token(user);

        return LoginResponseDTO.from_user(user, token);
    }
}
