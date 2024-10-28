package com.mabis.services;

import com.mabis.domain.user.LoginResponseDTO;
import com.mabis.domain.user.RegisterUserDTO;
import com.mabis.domain.user.User;
import com.mabis.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest
{
    @Mock
    UserRepository user_repository;

    @Mock
    JWTService jwt_service;

    @Mock
    PasswordEncoder password_encoder;

    @InjectMocks
    AuthService auth_service;

    @Test
    void test_successfull_user_creation()
    {
        // start mock
        RegisterUserDTO dto = new RegisterUserDTO(
                "test@gmail.com",
                "test",
                null,
                "test_password",
                "WAITER");
        User user = new User(dto);


        Mockito.when(user_repository.findByEmail(dto.email())).thenReturn(Optional.empty());
        Mockito.when(password_encoder.encode(dto.password())).thenReturn("hash");
        Mockito.when(user_repository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(jwt_service.generate_token(user)).thenReturn("token");
        // end mock

        LoginResponseDTO response = auth_service.register(dto);

        Mockito.verify(user_repository).save(Mockito.any(User.class));
        assertEquals(dto.first_name(), response.first_name());
        assertEquals(dto.role(), response.role());
    }

    @Test
    void test_register_email_already_in_use_throw_error()
    {

    }

    @Test
    void test_password_hash()
    {

    }
}