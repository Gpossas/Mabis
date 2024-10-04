package com.mabis.services;

import com.mabis.domain.restaurant_table.CreateTablesDTO;
import com.mabis.domain.restaurant_table.RestaurantTable;
import com.mabis.exceptions.TableNotFoundException;
import com.mabis.repositories.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TableService
{
    private final TableRepository table_repository;

    public void create_tables(CreateTablesDTO dto)
    {
        Optional<Integer> max_table_number = table_repository.get_max_table_number();

        ArrayList<RestaurantTable> tables = new ArrayList<>();
        for (int table_number = max_table_number.orElse(0) + 1, i = 0; i < dto.tables_quantity(); table_number++, i++)
        {
            tables.add(new RestaurantTable(table_number, dto.capacity()));
        }

        table_repository.saveAll(tables);
    }

    public void normalize_table_numbers_sequence()
    {
        List<RestaurantTable> tables = table_repository.findAll();
        int table_number = 1;
        for (RestaurantTable table: tables)
        {
            table.setNumber(table_number++);
        }

        table_repository.saveAll(tables);
    }

    public List<RestaurantTable> get_all_tables()
    {
        return table_repository.findAll();
    }

    public void delete_table_by_id(UUID id)
    {
        Optional<RestaurantTable> table = table_repository.findById(id);
        table.orElseThrow(TableNotFoundException::new);
        table_repository.deleteById(id);
    }

    public void delete_all_tables()
    {
        table_repository.deleteAll();
    }
}
