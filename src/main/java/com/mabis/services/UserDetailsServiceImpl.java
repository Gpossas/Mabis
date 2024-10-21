package com.mabis.services;

import com.mabis.domain.user.User;
import com.mabis.domain.user.UserDetailsImpl;
import com.mabis.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService
{
    private final UserRepository user_repository;

    @Override
    public UserDetailsImpl loadUserByUsername(String email)
    {
        User user = user_repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserDetailsImpl(user);
    }
}
