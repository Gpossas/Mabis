package com.mabis.domain.order;

import java.util.UUID;

public record ModifyOrderQuantityResponseDTO(UUID id, Integer quantity, Float Price) {}
