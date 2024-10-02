package com.mabis.controllers;

import com.mabis.domain.dish_type.CreateDishTypeDTO;
import com.mabis.domain.dish_type.ResponseDishTypeDTO;
import com.mabis.services.DishTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dish-types")
@RequiredArgsConstructor
public class DishTypeController
{
    private final DishTypeService dish_type_service;

    @PostMapping("/create")
    public ResponseEntity<ResponseDishTypeDTO> create_dish_type(@Valid @RequestBody CreateDishTypeDTO dish_type_dto)
    {
        return new ResponseEntity<>(dish_type_service.create_dish_type(dish_type_dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ResponseDishTypeDTO>> get_all_dish_types()
    {
        return ResponseEntity.ok(dish_type_service.get_all());
    }
}
