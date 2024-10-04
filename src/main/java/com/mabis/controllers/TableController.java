package com.mabis.controllers;

import com.mabis.domain.restaurant_table.CreateTablesDTO;
import com.mabis.domain.restaurant_table.RestaurantTable;
import com.mabis.services.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tables")
@RequiredArgsConstructor
public class TableController
{
    private final TableService table_service;

    @PostMapping("/create_tables")
    public ResponseEntity<String> create_tables(@RequestBody CreateTablesDTO dto)
    {
        table_service.create_tables(dto);
        return new ResponseEntity<>(
                "Created " + dto.tables_quantity() + " tables with " + dto.capacity() + " chairs" ,
                HttpStatus.CREATED);
    }

    @PatchMapping("/normalize_table_numbers")
    public ResponseEntity<String> normalize_table_numbers()
    {
        table_service.normalize_table_numbers_sequence();
        return new ResponseEntity<>("Normalized all table numbers", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantTable>> get_all_tables()
    {
        return new ResponseEntity<>(table_service.get_all_tables(), HttpStatus.OK);
    }
}
