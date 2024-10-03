package com.mabis.domain.restaurant_table;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateTablesDTO(
        @NotNull int capacity,
        @NotNull @Min(1) int tables_quantity
){}
