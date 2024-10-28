package com.mabis.domain.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
        @NotNull
        @Size(max = 100)
        String email,

        @NotNull
        @Size(min = 8)
        String password
) {
}
