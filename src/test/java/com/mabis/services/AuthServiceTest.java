package com.mabis.services;

import com.mabis.domain.user.LoginResponseDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest
{
    @InjectMocks
    AuthService auth_service;

    @Test
    void test_successfull_register_user()
    {

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