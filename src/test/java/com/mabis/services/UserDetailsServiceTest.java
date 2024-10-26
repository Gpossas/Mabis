package com.mabis.services;

import com.mabis.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


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
}
