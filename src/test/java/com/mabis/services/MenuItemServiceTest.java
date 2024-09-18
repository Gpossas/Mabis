package com.mabis.services;

import com.mabis.domain.dish_type.DishType;
import com.mabis.domain.menu_item.CreateMenuItemDTO;
import com.mabis.domain.menu_item.MenuItem;
import com.mabis.domain.menu_item.ResponseMenuItemDTO;
import com.mabis.repositories.MenuItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MenuItemServiceTest
{
    @Mock
    private MenuItemRepository menu_item_repository;

    @Mock
    private DishTypeService dish_type_service;

    @InjectMocks
    private MenuItemService menu_item_service;

    @Test
    void create_menu_item_required_fields()
    {
        DishType dish_type = new DishType();
        dish_type.setId(UUID.randomUUID());

        MenuItem menu_item_mock = new MenuItem(new CreateMenuItemDTO("risotto", 123F, null, null, null));
        menu_item_mock.setDish_type(dish_type);

        Mockito.when(dish_type_service.get_dish_type_by_id(dish_type.getId())).thenReturn(dish_type);
        Mockito.when(menu_item_repository.save(Mockito.any(MenuItem.class))).thenReturn(menu_item_mock);

        CreateMenuItemDTO dto = new CreateMenuItemDTO(
                menu_item_mock.getName(), menu_item_mock.getPrice(), null, dish_type.getId(),null);
        ResponseMenuItemDTO result = menu_item_service.create_menu_item(dto);

        assertEquals("Risotto", result.name());
        assertEquals(123, result.price());
    }
}