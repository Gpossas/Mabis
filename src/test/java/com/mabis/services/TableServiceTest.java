package com.mabis.services;

import com.mabis.domain.attachment.Attachment;
import com.mabis.domain.attachment.AttachmentUpload;
import com.mabis.domain.restaurant_table.CreateTablesDTO;
import com.mabis.domain.restaurant_table.RestaurantTable;
import com.mabis.exceptions.ActiveTableException;
import com.mabis.exceptions.NotActiveTableException;
import com.mabis.exceptions.TableNotFoundException;
import com.mabis.repositories.TableRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TableServiceTest
{
    @Mock
    TableRepository table_repository;

    @Mock
    QRCodeService qr_code_service;

    @Mock
    StorageServiceFactory storage_factory;

    @Mock
    ApplicationContext context;

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

    @Test
    void test_tables_normalization()
    {
        RestaurantTable table1 = new RestaurantTable(3,1);
        RestaurantTable table2 = new RestaurantTable(10,1);
        RestaurantTable table3 = new RestaurantTable(17,1);

        Mockito.when(table_repository.findAll()).thenReturn(List.of(table1, table2, table3));

        table_service.normalize_table_numbers_sequence();

        Mockito.verify(table_repository).saveAll(tables_list_captor.capture());

        assertIterableEquals(List.of(1, 2, 3), tables_list_captor.getValue().stream().map(RestaurantTable::getNumber).toList());
    }

    @Test
    void test_delete_non_existent_table_throw_error()
    {
        Mockito.when(table_repository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> table_service.delete_table_by_id(UUID.randomUUID()))
                .isInstanceOf(TableNotFoundException.class)
                .hasMessage("Table not found");
    }

    @Test
    void test_checkin_non_existent_table_throws_error()
    {
        Mockito.when(table_repository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> table_service.table_checkin(UUID.randomUUID()))
                .isInstanceOf(TableNotFoundException.class)
                .hasMessage("Table not found");
    }

    @Test
    void test_checkin_table_in_use_throws_error()
    {
        RestaurantTable table = new RestaurantTable(UUID.randomUUID(), 2, 2, "ACTIVE", null);

        Mockito.when(table_repository.findById(table.getId())).thenReturn(Optional.of(table));

        assertThatThrownBy(() -> table_service.table_checkin(table.getId()))
                .isInstanceOf(ActiveTableException.class)
                .hasMessage("A table can't be modified while active");
    }

    @Test
    void test_successful_table_checkin()
    {
        // start mock
        RestaurantTable table = Mockito.spy(new RestaurantTable(UUID.randomUUID(), 1, 2, "INACTIVE", null));

        Mockito.when(table_repository.findById(table.getId())).thenReturn(Optional.of(table));

        Mockito.when(qr_code_service.generate_qr_code(Mockito.anyString())).thenReturn(null);

        StorageService storage_service_mock = Mockito.mock(StorageService.class);
        Mockito.when(storage_factory.get_service("S3")).thenReturn(storage_service_mock);
        AttachmentService attachment_service_mock = Mockito.mock(AttachmentService.class);
        Mockito.when(context.getBean(AttachmentService.class, storage_service_mock)).thenReturn(attachment_service_mock);

        Mockito.when(attachment_service_mock.upload(Mockito.any(AttachmentUpload.class))).thenReturn("qr-code-url");
        // end mock

        table_service.table_checkin(table.getId());

        Mockito.verify(table).setQr_code(Mockito.any(Attachment.class));
        Mockito.verify(table).setStatus(RestaurantTable.table_status.ACTIVE.getStatus());
        assertEquals("active", table.getStatus());
        assertNotNull(table.getQr_code());
        assertEquals("qr-code-url", table.getQr_code().getUrl());
    }

    @Test
    void test_checkout_non_existent_table_throws_error()
    {
        Mockito.when(table_repository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> table_service.table_checkout(UUID.randomUUID()))
                .isInstanceOf(TableNotFoundException.class)
                .hasMessage("Table not found");
    }

    @Test
    void test_checkout_table_not_in_use_throws_error()
    {
        RestaurantTable table = new RestaurantTable(UUID.randomUUID(), 2, 2, "ACTIVE", null);

        Mockito.when(table_repository.findById(table.getId())).thenReturn(Optional.of(table));

        assertThatThrownBy(() -> table_service.table_checkout(table.getId()))
                .isInstanceOf(NotActiveTableException.class)
                .hasMessage("Table is not active");
    }
}