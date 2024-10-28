package com.mabis.domain.user;

public record LoginResponseDTO(String first_name, String role, String token)
{
    public static LoginResponseDTO from_user(User user, String token)
    {
        return new LoginResponseDTO(user.getFirst_name(), user.getRole().name(), token);
    }
}
