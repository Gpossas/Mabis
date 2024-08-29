package com.mabis.services;

import com.mabis.domain.dish_type.CreateDishTypeDTO;
import com.mabis.domain.dish_type.DishType;
import com.mabis.domain.dish_type.ResponseDishTypeDTO;
import com.mabis.repositories.DishTypeRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class DishTypeServiceTest
{
    @Mock
    private final DishTypeRepository dish_type_repository;

    @InjectMocks
    private final DishTypeService dish_type_service;

    @Test
    void create_dish_type_apply_uppercase()
    {
        // arrange
        DishType dish_type_mock = new DishType();
        dish_type_mock.setName("starter");

        //mock behavior of the repository
        Mockito.when(dish_type_repository.save(dish_type_mock)).thenReturn(dish_type_mock);

        // act
        CreateDishTypeDTO dish_type_dto_mock = new CreateDishTypeDTO(dish_type_mock.getName());
        ResponseDishTypeDTO result = dish_type_service.create_dish_type(dish_type_dto_mock);

        //assert
        Mockito.verify(dish_type_repository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Starter", result.name());
    }
}