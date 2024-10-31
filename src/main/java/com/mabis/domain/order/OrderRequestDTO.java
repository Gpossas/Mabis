package com.mabis.domain.order;

import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;


public record OrderRequestDTO(@NotNull UUID table_id, Set<OrderItemDTO> menu_items) {}
