package com.mabis.domain.category;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryDTO(@NotBlank String name) {}
