package com.mabis.services;

import com.mabis.domain.dish_type.CreateDishTypeDTO;
import com.mabis.domain.dish_type.DishType;
import com.mabis.domain.dish_type.ResponseDishTypeDTO;
import com.mabis.repositories.DishTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishTypeService
{
    private final DishTypeRepository dish_type_repository;

    public ResponseDishTypeDTO create_dish_type(CreateDishTypeDTO dish_type_dto)
    {
        DishType dish_type = new DishType();
        dish_type.setName(dish_type_dto.name());
        dish_type = dish_type_repository.save(dish_type);
        return new ResponseDishTypeDTO(dish_type.getId(), dish_type.getName());
    }

    public List<ResponseDishTypeDTO> get_all()
    {
        return dish_type_repository.findAllDishTypesSorted();
    }
}
