package com.mabis.services;

import com.mabis.domain.dish_type.CreateDishTypeDTO;
import com.mabis.domain.dish_type.DishType;
import com.mabis.domain.dish_type.ResponseDishTypeDTO;
import com.mabis.exceptions.DishTypeNotFoundException;
import com.mabis.repositories.DishTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DishTypeService
{
    private final DishTypeRepository dish_type_repository;

    public ResponseDishTypeDTO create_dish_type(CreateDishTypeDTO dish_type_dto)
    {
        DishType dish_type = new DishType();
        String captalized_name = dish_type_dto.name().substring(0,1).toUpperCase() + dish_type_dto.name().substring(1);
        dish_type.setName(captalized_name);
        dish_type = dish_type_repository.save(dish_type);
        return new ResponseDishTypeDTO(dish_type.getId(), dish_type.getName());
    }

    public List<ResponseDishTypeDTO> get_all()
    {
        return dish_type_repository.findAllDishTypesSorted();
    }

    public DishType get_dish_type_by_id(UUID id)
    {
        Optional<DishType> dish_type = dish_type_repository.findById(id);
        if (dish_type.isEmpty())
        {
            throw new DishTypeNotFoundException();
        }
        return dish_type.get();
    }
}
