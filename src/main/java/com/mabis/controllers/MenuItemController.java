package com.mabis.controllers;

import com.mabis.domain.menu_item.CreateMenuItemDTO;
import com.mabis.domain.menu_item.ResponseMenuItemDTO;
import com.mabis.services.MenuItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu-items")
@RequiredArgsConstructor
public class MenuItemController
{
    private final MenuItemService menu_item_service;

    @PostMapping("/create")
    public ResponseEntity<ResponseMenuItemDTO> create_menu_item(@Valid @RequestBody CreateMenuItemDTO menu_item_dto)
    {
        return new ResponseEntity<>(menu_item_service.create_menu_item(menu_item_dto), HttpStatus.CREATED);
    }
}
