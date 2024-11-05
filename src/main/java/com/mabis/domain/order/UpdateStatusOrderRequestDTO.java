package com.mabis.domain.order;

import com.mabis.infra.ValidEnum;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateStatusOrderRequestDTO(
        @NotNull
        UUID order_id,

        @NotNull
        @ValidEnum(enumClass = Order.OrderStatus.class)
        String order_status
) {}
