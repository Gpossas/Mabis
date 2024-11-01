package com.mabis.services;

import com.mabis.domain.menu_item.MenuItem;
import com.mabis.domain.order.Order;
import com.mabis.domain.order.OrderItemDTO;
import com.mabis.domain.order.OrderRequestDTO;
import com.mabis.domain.restaurant_table.RestaurantTable;
import com.mabis.exceptions.NotActiveTableException;
import com.mabis.exceptions.TableTokenNotMatchException;
import com.mabis.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService
{
    private final TableService table_service;
    private final MenuItemService menu_item_service;
    private final OrderRepository order_repository;

    public void place_order(OrderRequestDTO dto)
    {
        RestaurantTable table = table_service.get_table_by_id(dto.table_id());
        this.verify_table_status_active(table);
        this.verify_credentials_authorized(table, dto.table_token());

        List<Order> orders = new ArrayList<>();
        for (OrderItemDTO order_item: dto.menu_items())
        {
            MenuItem menu_item = menu_item_service.get_by_id(order_item.menu_item_id());
            orders.add(new Order(order_item.quantity(), menu_item.getPrice(), order_item.description(), table, menu_item));
        }

        order_repository.saveAll(orders);
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
        if (table.getQr_code() != null && !table.getQr_code().getName().equals(token)
            || SecurityContextHolder.getContext().getAuthentication()
            .getAuthorities().stream()
            .anyMatch(authority ->
                    authority.equals(new SimpleGrantedAuthority("ROLE_OWNER")) ||
                    authority.equals(new SimpleGrantedAuthority("ROLE_WAITER"))
            ))
        {
            throw new TableTokenNotMatchException(table.getNumber());
        }
    }
}
