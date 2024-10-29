package com.mabis.services;

import com.mabis.domain.restaurant_table.CreateTablesDTO;
import com.mabis.domain.restaurant_table.RestaurantTable;
import com.mabis.repositories.TableRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TableServiceTest
{
    @Mock
    TableRepository table_repository;

    @InjectMocks
    TableService table_service;

    @Captor
    ArgumentCaptor<List<RestaurantTable>> tables_list_captor;

    @Test
    void test_create_tables()
    {
        int MAX_TABLE_NUMBER = 200;
        int EXPECTED_TABLES_CREATED_COUNT = 21;
        int EXPECTED_LAST_TABLE_NUMBER = MAX_TABLE_NUMBER + EXPECTED_TABLES_CREATED_COUNT;

        Mockito.when(table_repository.get_max_table_number()).thenReturn(Optional.of(MAX_TABLE_NUMBER));

        table_service.create_tables(new CreateTablesDTO(1, EXPECTED_TABLES_CREATED_COUNT));

        Mockito.verify(table_repository).saveAll(tables_list_captor.capture());

        assertEquals(EXPECTED_TABLES_CREATED_COUNT, tables_list_captor.getValue().size());
        assertEquals(EXPECTED_LAST_TABLE_NUMBER, tables_list_captor.getValue().removeLast().getNumber());
    }
}