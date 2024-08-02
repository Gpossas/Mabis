package com.mabis.domain.dish_type;

import jakarta.validation.constraints.NotBlank;

public record CreateDishTypeDTO(@NotBlank String name) {}
