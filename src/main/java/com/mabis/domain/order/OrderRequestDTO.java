package com.mabis.domain.order;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;


public record OrderRequestDTO(@NotNull UUID table_id, @NotNull String table_token, @NotNull List<OrderItemDTO> menu_items) {}
