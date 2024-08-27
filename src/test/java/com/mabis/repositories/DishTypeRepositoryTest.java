package com.mabis.repositories;

import com.mabis.domain.dish_type.DishType;
import com.mabis.domain.dish_type.ResponseDishTypeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class DishTypeRepositoryTest
{
    @Autowired
    DishTypeRepository dish_type_repository;

    @Test
    void findAllDishTypesSorted()
    {
        DishType dish = new DishType();
        dish.setName("starter");
        dish_type_repository.save(dish);

        dish = new DishType();
        dish.setName("desert");
        dish_type_repository.save(dish);

        dish = new DishType();
        dish.setName("main");
        dish_type_repository.save(dish);

        String[] result = dish_type_repository
                .findAllDishTypesSorted()
                .stream()
                .map(ResponseDishTypeDTO::name)
                .toArray(String[]::new);

        String[] expected = {"desert", "main", "starter"};

        assertArrayEquals(expected, result);
    }

    @Test
    void return_empty_list_if_no_dish_types()
    {
        List<ResponseDishTypeDTO> result = dish_type_repository.findAllDishTypesSorted();
        assertTrue(result.isEmpty());
    }
}

