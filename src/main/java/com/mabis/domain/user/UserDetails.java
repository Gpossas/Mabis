package com.mabis.domain.user;

public interface UserDetails extends org.springframework.security.core.userdetails.UserDetails
{
    User get_user();
}
