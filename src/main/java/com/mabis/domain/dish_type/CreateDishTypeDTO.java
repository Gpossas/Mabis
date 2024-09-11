package com.mabis.domain.dish_type;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

public record CreateDishTypeDTO(@NotBlank @Max(30) String name) {}
