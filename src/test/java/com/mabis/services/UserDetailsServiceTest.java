package com.mabis.services;

import com.mabis.domain.user.RegisterUserDTO;
import com.mabis.domain.user.User;
import com.mabis.domain.user.UserDetails;
import com.mabis.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest
{
    @Mock
    UserRepository user_repository;

    @InjectMocks
    UserDetailsServiceImpl user_details_service;

    @Test
    void test_throw_error_user_not_exists()
    {
        Mockito.when(user_repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> user_details_service.loadUserByUsername(Mockito.anyString()))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User not found");
    }

    @Test
    void test_successful_load_user_by_username()
    {
        RegisterUserDTO dto = new RegisterUserDTO("gui@gmail.com", "gui", null, "password", "WAITER");
        User user = new User(dto);
        user.setPassword("password");
        user_repository.save(user);

        Mockito.when(user_repository.findByEmail("gui@gmail.com")).thenReturn(Optional.of(user));

        UserDetails userDetails = user_details_service.loadUserByUsername(user.getEmail());

        assertEquals("gui@gmail.com", userDetails.getUsername());
    }

    @Test
    void test_correct_set_authorities()
    {
        RegisterUserDTO dto = new RegisterUserDTO("gui@gmail.com", "gui", null, "password", "WAITER");
        User user = new User(dto);
        user.setPassword("password");
        user_repository.save(user);

        Mockito.when(user_repository.findByEmail("gui@gmail.com")).thenReturn(Optional.of(user));

        UserDetails userDetails = user_details_service.loadUserByUsername(user.getEmail());

        assertIterableEquals(Collections.singleton(new SimpleGrantedAuthority("WAITER")), userDetails.getAuthorities());
    }
}
