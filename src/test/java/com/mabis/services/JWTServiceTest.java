package com.mabis.services;

import com.auth0.jwt.algorithms.Algorithm;
import com.mabis.domain.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class JWTServiceTest
{
    @InjectMocks
    JWTService jwt_service;

    @Test
    void test_generate_token_throw_error_if_secret_not_set()
    {
        String invalidSecret = "";

        // Mock the static method Algorithm.HMAC256
        try (MockedStatic<Algorithm> mockedAlgorithm = mockStatic(Algorithm.class)) {
            // Define the behavior when HMAC256 is called with an empty secret
            mockedAlgorithm.when(() -> Algorithm.HMAC256(invalidSecret))
                    .thenThrow(new IllegalArgumentException());

            // Now, call the method that generates the token and verify the exception is thrown
            assertThatThrownBy(() -> jwt_service.generate_token(Mockito.mock(User.class)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The Algorithm cannot be null.");
        }
    }
}