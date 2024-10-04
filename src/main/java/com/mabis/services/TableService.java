package com.mabis.services;

import com.google.zxing.WriterException;
import com.mabis.domain.attachment.AttachmentService;
import com.mabis.domain.attachment.StorageService;
import com.mabis.domain.attachment.StorageServiceFactory;
import com.mabis.domain.restaurant_table.CreateTablesDTO;
import com.mabis.domain.restaurant_table.RestaurantTable;
import com.mabis.exceptions.ActiveTableException;
import com.mabis.exceptions.TableNotFoundException;
import com.mabis.repositories.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TableService
{
    private final TableRepository table_repository;
    private final QRCodeService qr_code_service;
    private final StorageServiceFactory storage_factory;
    private final ApplicationContext context;

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

    public void table_checkin() throws WriterException {
        RestaurantTable table = table_repository.findById().orElseThrow(TableNotFoundException::new);

        if (table.getStatus().equals(RestaurantTable.table_status.ACTIVE.getStatus()))
        {
            throw new ActiveTableException();
        }

        String token = String.valueOf(table.getNumber()) + UUID.randomUUID();
        String url = "http://localhost:8080/tables/checkin?token=" + token;

        BufferedImage qr_code_image = qr_code_service.generate_qr_code_image(url);

        StorageService storage_service = storage_factory.get_service("S3");
        AttachmentService attachment_service = context.getBean(AttachmentService.class, storage_service);
        attachment_service.upload(qr_code_image);


        table.setStatus("active");

    }

}
