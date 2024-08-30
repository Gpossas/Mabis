package com.mabis.services;

import com.mabis.domain.dish_type.CreateDishTypeDTO;
import com.mabis.domain.dish_type.DishType;
import com.mabis.domain.dish_type.ResponseDishTypeDTO;
import com.mabis.exceptions.NotFound;
import com.mabis.repositories.DishTypeRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @Test
    void find_dish_by_id()
    {
        DishType dish_type_mock = new DishType();
        dish_type_mock.setName("starter");
        dish_type_mock.setId(UUID.randomUUID());

        Mockito.when(dish_type_repository.findById(dish_type_mock.getId())).thenReturn(Optional.of(dish_type_mock));

        DishType result = dish_type_service.get_dish_type_by_id(dish_type_mock.getId());

        Mockito.verify(dish_type_repository, Mockito.times(1)).findById(Mockito.any());
        assertEquals("Starter", result.getName());
    }

    @Test
    void throw_not_found_if_no_id()
    {
        UUID id = UUID.randomUUID();
        Mockito.when(dish_type_repository.findById(id)).thenReturn(null);

        assertThatThrownBy(() -> dish_type_service.get_dish_type_by_id(id))
                .isInstanceOf(NotFound.class)
                .hasMessage("Dish Type not found");
    }
}