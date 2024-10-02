package com.mabis.domain.category;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCategoryDTO(@NotBlank @Size(min = 3, max = 30) String name) {}
