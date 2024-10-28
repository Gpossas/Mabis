package com.mabis.services;

import com.mabis.domain.user.*;
import com.mabis.exceptions.UnmatchPassword;
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
    private final UserDetailsServiceImpl user_details_service;


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

    public LoginResponseDTO login(LoginRequestDTO credentials)
    {
        UserDetails user_details = user_details_service.loadUserByUsername(credentials.email());
        if (!password_encoder.matches(credentials.password(), user_details.getPassword()))
        {
            throw new UnmatchPassword();
        }

        String token = jwt_service.generate_token(user_details.get_user());

        return LoginResponseDTO.from_user(user_details.get_user(), token);
    }
}
