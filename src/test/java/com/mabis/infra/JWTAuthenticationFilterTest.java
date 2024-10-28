package com.mabis.infra;

import com.mabis.domain.user.RegisterUserDTO;
import com.mabis.domain.user.User;
import com.mabis.domain.user.UserDetails;
import com.mabis.domain.user.UserDetailsImpl;
import com.mabis.services.JWTService;
import com.mabis.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JWTAuthenticationFilterTest
{
    private MockHttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filter_chain;

    @Mock
    JWTService jwt_service;

    @Mock
    UserDetailsServiceImpl user_details_service;

    @InjectMocks
    JWTAuthenticationFilter jwt_authentication_filter;

    @BeforeEach
    public void setup()
    {
        this.request = new MockHttpServletRequest();
        this.response = Mockito.mock(HttpServletResponse.class);
        this.filter_chain = Mockito.mock(FilterChain.class);
    }

    @Test
    void test_correctly_set_authorization_to_user() throws ServletException, IOException
    {
        ArgumentCaptor<Authentication> authentication_captor = ArgumentCaptor.forClass(Authentication.class);

        // start mock
        request.addHeader("Authorization", "Bearer my-token");

        Mockito.when(jwt_service.validate_token("my-token")).thenReturn("test@gmail.com");

        RegisterUserDTO dto = new RegisterUserDTO(
                "test@gmail.com",
                "test",
                null,
                "test",
                "WAITER");
        User user = new User(dto);
        UserDetails userDetails = new UserDetailsImpl(user);
        Mockito.when(user_details_service.loadUserByUsername("test@gmail.com")).thenReturn(userDetails);

        SecurityContext context_mock = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(context_mock);
        // end mock

        jwt_authentication_filter.doFilterInternal(request, response, filter_chain);

        Mockito.verify(context_mock).setAuthentication(authentication_captor.capture());

        assertIterableEquals(
                Collections.singleton(new SimpleGrantedAuthority("WAITER")),
                authentication_captor.getValue().getAuthorities());
    }
}