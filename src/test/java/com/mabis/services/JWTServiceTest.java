package com.mabis.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class JWTServiceTest
{


    @Test
    void test_generate_token_throw_error_if_secret_not_set()
    {
        Algorithm algorithm = Algorithm.HMAC256("");

        assertThatThrownBy(() -> JWT.create()
                .withIssuer("")
                .withSubject("")
                .withExpiresAt(Mockito.mock(Date.class))
                .sign(algorithm)
        ).isInstanceOf(IllegalArgumentException.class)
         .hasMessage("Empty key");
    }

    @Test
    void validate_token()
    {

    }
}