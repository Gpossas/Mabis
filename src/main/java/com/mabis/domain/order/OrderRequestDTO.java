package com.mabis.domain.order;

import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;


public record OrderRequestDTO(@NotNull UUID table_id, @NotNull String table_token, @NotNull Set<OrderItemDTO> menu_items) {}
