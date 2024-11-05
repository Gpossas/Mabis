package com.mabis.domain.order;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ModifyOrderQuantityRequestDTO(@NotNull UUID order_id, @NotNull Integer quantity) {
}
