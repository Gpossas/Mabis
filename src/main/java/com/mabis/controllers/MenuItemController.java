package com.mabis.controllers;

import com.mabis.domain.menu_item.CreateMenuItemDTO;
import com.mabis.domain.menu_item.ResponseMenuItemDTO;
import com.mabis.services.MenuItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/menu-items")
@RequiredArgsConstructor
public class MenuItemController
{
    private final MenuItemService menu_item_service;

    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public ResponseEntity<ResponseMenuItemDTO> create_menu_item(@Valid @ModelAttribute CreateMenuItemDTO menu_item_dto)
    {
        return new ResponseEntity<>(menu_item_service.create_menu_item(menu_item_dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ResponseMenuItemDTO>> get_all_menu_items()
    {
        return new ResponseEntity<>(menu_item_service.get_all_menu_items(), HttpStatus.OK);
    }
}
