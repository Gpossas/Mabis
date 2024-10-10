package com.mabis.services;

import com.mabis.domain.attachment.*;
import com.mabis.domain.restaurant_table.CreateTablesDTO;
import com.mabis.domain.restaurant_table.RestaurantTable;
import com.mabis.exceptions.ActiveTableException;
import com.mabis.exceptions.NotActiveTableException;
import com.mabis.exceptions.TableNotFoundException;
import com.mabis.repositories.AttachmentRepository;
import com.mabis.repositories.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TableService
{
    private final TableRepository table_repository;
    private final AttachmentRepository attachment_repository;
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

    public String table_checkin(UUID id)
    {
        RestaurantTable table = table_repository.findById(id).orElseThrow(TableNotFoundException::new);

        if (table.getStatus().equals(RestaurantTable.table_status.ACTIVE.getStatus()))
        {
            throw new ActiveTableException();
        }

        String token = String.valueOf(table.getNumber()) + UUID.randomUUID();

        byte[] qr_code_bytes = qr_code_service.generate_qr_code(table_token_url + token);
        AttachmentUpload qr_code_upload = new QRCodeAttachmentUpload(token, qr_code_bytes);

        StorageService storage_service = storage_factory.get_service("S3");
        AttachmentService attachment_service = context.getBean(AttachmentService.class, storage_service);
        String image_url = attachment_service.upload(qr_code_upload);

        table.setStatus(RestaurantTable.table_status.ACTIVE.getStatus());
        Attachment qr_code = new Attachment(token, image_url);
        table.setQr_code(qr_code);
        table_repository.save(table);

        return image_url;
    }

    public void table_checkout(UUID id)
    {
        RestaurantTable table = table_repository.findById(id).orElseThrow(TableNotFoundException::new);

        if (!table.getStatus().equals(RestaurantTable.table_status.ACTIVE.getStatus()))
        {
            throw new NotActiveTableException();
        }

        StorageService storage_service = storage_factory.get_service("S3");
        AttachmentService attachment_service = context.getBean(AttachmentService.class, storage_service);
        attachment_service.delete(table.getQr_code().getName());

        Attachment qr_code = table.getQr_code();
        table.setQr_code(null);
        table.setStatus(RestaurantTable.table_status.INACTIVE.getStatus());
        table_repository.save(table);

        attachment_repository.delete(qr_code);
    }
}
