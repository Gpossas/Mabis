package com.mabis.domain.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserDTO(
        @NotBlank
        @Size(max = 100)
        String email,

        @NotBlank
        @Size(max = 20)
        String first_name,

        @Size(max = 50)
        String last_name,

        @NotBlank
        @Size(min = 8)
        String password,

        @NotBlank
        @Size(max = 6)
        User.Roles role
) {}
