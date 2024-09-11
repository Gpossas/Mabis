package com.mabis.domain.category;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

public record CreateCategoryDTO(@NotBlank @Max(30) String name) {}
