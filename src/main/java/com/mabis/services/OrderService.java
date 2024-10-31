package com.mabis.services;

import com.mabis.domain.order.OrderRequestDTO;
import com.mabis.domain.restaurant_table.RestaurantTable;
import com.mabis.exceptions.NotActiveTableException;
import com.mabis.exceptions.TableTokenNotMatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService
{
    private final TableService table_service;

    public void place_order(OrderRequestDTO dto)
    {
        RestaurantTable table = table_service.get_table_by_id(dto.table_id());
        this.verify_table_status_active(table);
        this.verify_credentials_authorized(table, dto.table_token());

    }

    private void verify_table_status_active(RestaurantTable table)
    {
        if (!table.getStatus().equals(RestaurantTable.table_status.ACTIVE.getStatus()))
        {
            throw new NotActiveTableException();
        }
    }

    private void verify_credentials_authorized(RestaurantTable table, String token)
    {
        if (table.getQr_code() != null && !table.getQr_code().getName().equals(token))
        {
            throw new TableTokenNotMatchException(table.getNumber());
        }
    }
}
