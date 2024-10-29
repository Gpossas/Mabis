package com.mabis.repositories;

import com.mabis.domain.restaurant_table.RestaurantTable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TableRepositoryTest
{
    @Autowired
    TableRepository table_repository;

    @Test
    void test_get_max_table_number()
    {
        RestaurantTable table = new RestaurantTable(200, 2);
        RestaurantTable table2 = new RestaurantTable(204, 4);
        RestaurantTable table3 = new RestaurantTable(201, 5);
        table_repository.saveAll(List.of(table, table2, table3));

        Optional<Integer> response = table_repository.get_max_table_number();
        assertEquals(204, response.orElse(1));
    }
}