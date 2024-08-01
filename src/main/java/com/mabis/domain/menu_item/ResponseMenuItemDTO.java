package com.mabis.domain.menu_item;

import java.util.UUID;

public record ResponseMenuItemDTO(
        UUID id,
        String name,
        Float price,
        String description,
        String image_url
) {}
//TODO: include relationships: dish_type and category