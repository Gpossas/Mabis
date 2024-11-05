package com.mabis.domain.order;

import java.util.UUID;

public record OrderClientResponseDTO(UUID id, Integer quantity, Float price, String description)
{
    public static OrderClientResponseDTO to_client_response(Order order)
    {
        return new OrderClientResponseDTO(order.getId(), order.getQuantity(), order.getPrice(), order.getDescription());
    }
}
