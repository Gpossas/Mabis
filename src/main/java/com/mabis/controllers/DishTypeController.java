package com.mabis.controllers;

import com.mabis.domain.dish_type.CreateDishTypeDTO;
import com.mabis.domain.dish_type.ResponseDishTypeDTO;
import com.mabis.services.DishTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dish-types")
@RequiredArgsConstructor
public class DishTypeController
{
    private final DishTypeService dish_type_service;

    @PostMapping("/create")
    public ResponseDishTypeDTO create_dish_type(@RequestBody CreateDishTypeDTO dish_type_dto)
    {
        return dish_type_service.create_dish_type(dish_type_dto);
    }

    @GetMapping
    public List<ResponseDishTypeDTO> get_all_dish_types()
    {
        return dish_type_service.get_all();
    }
}
