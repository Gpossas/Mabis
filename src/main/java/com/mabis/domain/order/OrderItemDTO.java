package com.mabis.domain.order;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderItemDTO(
        @NotNull
        UUID menu_item_id,

        @NotNull
        @Min(1)
        @Max(20)
        Integer quantity,

        String description
) {}
