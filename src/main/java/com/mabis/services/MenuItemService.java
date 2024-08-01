package com.mabis.services;

import com.mabis.domain.dish_type.DishType;
import com.mabis.domain.menu_item.CreateMenuItemDTO;
import com.mabis.domain.menu_item.MenuItem;
import com.mabis.domain.menu_item.ResponseMenuItemDTO;
import com.mabis.repositories.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuItemService
{
    private final MenuItemRepository menu_item_repository;
    private final DishTypeService dish_type_service;

    public ResponseMenuItemDTO create_menu_item(CreateMenuItemDTO menu_item_dto)
    {
        // TODO: handle when the dish_type id doesn't exist in database
        // TODO: upload the multipart file to amazon s3, but use a Interface to keep things decoupled
        MenuItem menu_item = new MenuItem();
        menu_item.setName(menu_item_dto.name());
        menu_item.setPrice(menu_item_dto.price());
        menu_item.setDescription(menu_item_dto.description());

        DishType dish_type = dish_type_service.get_dish_type_by_id(menu_item_dto.dish_type_id());
        menu_item.setDish_type(dish_type);

        menu_item = menu_item_repository.save(menu_item);

        return new ResponseMenuItemDTO(
                menu_item.getId(),
                menu_item.getName(),
                menu_item.getPrice(),
                menu_item.getDescription(),
                menu_item.getImage_url()
        );
    }
}
