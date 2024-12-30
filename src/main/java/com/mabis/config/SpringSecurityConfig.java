package com.mabis.config;

import com.mabis.infra.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig
{
    private final JWTAuthenticationFilter jwt_authentication_filter;

    @Bean
    public SecurityFilterChain filter_chain(HttpSecurity http) throws Exception
    {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/categories/**").hasAuthority("OWNER")
                        .requestMatchers(HttpMethod.POST, "/dish-types/**").hasAuthority("OWNER")
                        .requestMatchers(HttpMethod.POST, "/menu-items/**").hasAuthority("OWNER")
                        .requestMatchers(HttpMethod.POST, "/tables/**").hasAuthority("OWNER")
                        .requestMatchers(HttpMethod.PATCH, "/tables/**").hasAnyAuthority("OWNER", "WAITER")
                        .requestMatchers(HttpMethod.DELETE, "/tables/**").hasAuthority("OWNER")
                        .requestMatchers(HttpMethod.PATCH, "/orders/status").hasAnyAuthority("OWNER", "COOK")
                        .requestMatchers(HttpMethod.PATCH, "/orders/quantity").hasAnyAuthority("OWNER", "WAITER")
                        .requestMatchers(HttpMethod.DELETE, "/orders/quantity").hasAnyAuthority("OWNER", "WAITER")
                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwt_authentication_filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder password_encoder()
    {
        return new BCryptPasswordEncoder();
    }
}
