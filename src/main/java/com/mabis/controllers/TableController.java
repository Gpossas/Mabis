package com.mabis.controllers;

import com.mabis.domain.restaurant_table.CreateTablesDTO;
import com.mabis.services.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
