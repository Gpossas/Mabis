package com.mabis.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.mabis.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JWTService
{
    @Value("${api.jwt.secret}")
    private String secret;

    public String generate_token(User user)
    {
        return JWT.create()
                .withIssuer("mabis-api")
                .withSubject(user.getEmail())
                .withExpiresAt(LocalDateTime.now().plusHours(6).toInstant(ZoneOffset.of("-03:00")))
                .sign(Algorithm.HMAC256(secret));
    }

    public String validate_token(String token)
    {
        try
        {
            return JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("mabis-api")
                    .build()
                    .verify(token)
                    .getSubject();
        }
        catch (JWTVerificationException exception)
        {
            return null;
        }
    }
}
