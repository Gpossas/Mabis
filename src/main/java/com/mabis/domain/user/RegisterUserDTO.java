package com.mabis.domain.user;

import com.mabis.infra.ValidEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record RegisterUserDTO(
        @NotBlank
        @Size(max = 100)
        @Email
        String email,

        @NotBlank
        @Size(max = 20)
        String first_name,

        @Size(max = 50)
        String last_name,

        @NotBlank
        @Size(min = 8)
        String password,

        @ValidEnum(enumClass = User.Roles.class, message = "Invalid role")
        String role
) {}