package com.mabis.services;

import com.google.zxing.WriterException;
import com.mabis.domain.attachment.*;
import com.mabis.domain.restaurant_table.CreateTablesDTO;
import com.mabis.domain.restaurant_table.RestaurantTable;
import com.mabis.exceptions.ActiveTableException;
import com.mabis.exceptions.TableNotFoundException;
import com.mabis.repositories.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TableService
{
    private final TableRepository table_repository;
    private final QRCodeService qr_code_service;
    private final StorageServiceFactory storage_factory;
    private final ApplicationContext context;
    @Value("${table.receive_token.url}")
    private String table_token_url;

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

    public String table_checkin(UUID id) throws WriterException, IOException {
        RestaurantTable table = table_repository.findById(id).orElseThrow(TableNotFoundException::new);

        if (table.getStatus().equals(RestaurantTable.table_status.ACTIVE.getStatus()))
        {
            throw new ActiveTableException();
        }

        String token = String.valueOf(table.getNumber()) + UUID.randomUUID();

        byte[] qr_code_bytes = qr_code_service.generate_qr_code(table_token_url + token);
        Attachment qr_code = new QRCodeAttachment(qr_code_bytes);

        StorageService storage_service = storage_factory.get_service("S3");
        AttachmentService attachment_service = context.getBean(AttachmentService.class, storage_service);
        String image_url = attachment_service.upload(qr_code);

        table.setStatus("active");
        table.setQr_code_url(image_url);
        table_repository.save(table);

        return table.getQr_code_url();
    }

}
