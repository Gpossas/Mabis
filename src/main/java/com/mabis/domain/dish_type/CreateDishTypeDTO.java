package com.mabis.domain.dish_type;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateDishTypeDTO(@NotBlank @Size(min = 3, max = 30) String name) {}
