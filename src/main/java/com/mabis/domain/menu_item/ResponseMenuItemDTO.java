package com.mabis.domain.menu_item;

import java.util.UUID;

public record ResponseMenuItemDTO(
        UUID id,
        String name,
        Float price,
        String description,
        String image_url
) {
    public static ResponseMenuItemDTO from_menu_item(MenuItem menu_item)
    {
        return new ResponseMenuItemDTO(
                menu_item.getId(),
                menu_item.getName(),
                menu_item.getPrice(),
                menu_item.getDescription(),
                menu_item.getImage_url()
        );
    }
}
//TODO: include relationships: dish_type and category