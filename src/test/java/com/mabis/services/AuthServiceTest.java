package com.mabis.services;

import com.mabis.domain.user.*;
import com.mabis.exceptions.UnmatchPassword;
import com.mabis.exceptions.UserEmailAlreadyInUse;
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

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @Mock
    UserDetailsServiceImpl user_details_service;

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
        RegisterUserDTO dto = new RegisterUserDTO("test@email.com", null, null, null, null);

        Mockito.when(user_repository.findByEmail("test@email.com"))
                .thenReturn(Optional.of(Mockito.mock(User.class)));

        assertThatThrownBy(() -> auth_service.register(dto))
                .isInstanceOf(UserEmailAlreadyInUse.class)
                .hasMessage("This email is already in use");
    }

    @Test
    void test_password_hash()
    {
        // start mock
        RegisterUserDTO dto = new RegisterUserDTO("", null, null, "test_password", "WAITER");

        Mockito.when(user_repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(password_encoder.encode(dto.password())).thenReturn("hash_password");
        Mockito.when(user_repository.save(Mockito.any(User.class))).thenReturn(new User(dto));

        ArgumentCaptor<User> user_captor = ArgumentCaptor.forClass(User.class);
        //end mock

        auth_service.register(dto);

        Mockito.verify(user_repository).save(user_captor.capture());

        assertNotEquals("test_password", user_captor.getValue().getPassword());
        assertEquals("hash_password", user_captor.getValue().getPassword());
    }

    @Test
    void test_error_throw_if_password_do_not_match_in_login()
    {
        UserDetails user = Mockito.mock(UserDetails.class);
        LoginRequestDTO dto = Mockito.mock(LoginRequestDTO.class);
        Mockito.when(user_details_service.loadUserByUsername(Mockito.any())).thenReturn(user);
        Mockito.when(password_encoder.matches(Mockito.any(), Mockito.any())).thenReturn(false);

        assertThatThrownBy(() -> auth_service.login(dto))
                .isInstanceOf(UnmatchPassword.class)
                .hasMessage("Password doesn't match");
    }
}