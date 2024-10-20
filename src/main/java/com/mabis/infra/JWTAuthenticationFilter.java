package com.mabis.infra;

import com.mabis.domain.user.UserDetailsImpl;
import com.mabis.services.JWTService;
import com.mabis.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter
{
    private final JWTService jwt_service;
    private final UserDetailsServiceImpl user_details_service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        String token = this.get_token(request);
        String login = jwt_service.validate_token(token);

        if (login != null)
        {
            UserDetailsImpl user = (UserDetailsImpl) user_details_service.loadUserByUsername(login);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String get_token(HttpServletRequest request)
    {
        String auth_header = request.getHeader("Authorization");
        if (auth_header == null)
            return null;
        return auth_header.replace("Bearer ", "");
    }
}
