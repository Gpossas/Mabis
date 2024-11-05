package com.mabis.services;

import com.mabis.domain.menu_item.MenuItem;
import com.mabis.domain.order.*;
import com.mabis.domain.restaurant_table.RestaurantTable;
import com.mabis.exceptions.NotActiveTableException;
import com.mabis.exceptions.OrderNotFoundException;
import com.mabis.exceptions.TableTokenNotMatchException;
import com.mabis.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService
{
    private final TableService table_service;
    private final MenuItemService menu_item_service;
    private final OrderRepository order_repository;

    public List<OrderClientResponseDTO> place_order(OrderRequestDTO dto)
    {
        RestaurantTable table = table_service.get_table_by_id(dto.table_id());
        this.verify_table_status_active(table);
        this.verify_credentials_authorized(table, dto.table_token());

        Set<UUID> ids_menu_items = dto.menu_items().stream().map(OrderItemDTO::menu_item_id).collect(Collectors.toSet());
        Map<UUID, MenuItem> menu_item_map = this.menu_item_service.find_id_in(ids_menu_items)
                .stream().collect(Collectors.toMap(MenuItem::getId, item -> item));
        List<Order> orders = new ArrayList<>();
        for (OrderItemDTO order_item: dto.menu_items())
        {
            MenuItem menu_item = menu_item_map.get(order_item.menu_item_id());
            if (menu_item == null)
            {
                continue;
            }

            orders.add(new Order(order_item.quantity(), menu_item.getPrice(), order_item.description(), table, menu_item));
        }

        orders = order_repository.saveAll(orders);
        return orders.stream().map(OrderClientResponseDTO::to_client_response).toList();
    }

    public void delete_order(UUID id)
    {
        order_repository.findById(id).orElseThrow(OrderNotFoundException::new);
        order_repository.deleteById(id);
    }

    public Order.OrderStatus update_status_order(UpdateStatusOrderRequestDTO dto)
    {
       Order order = order_repository.findById(dto.order_id()).orElseThrow(OrderNotFoundException::new);
       order.setStatus(Order.OrderStatus.valueOf(dto.order_status()));
       order.setStatus_updated_at(LocalDateTime.now());
       order_repository.save(order);
       return order.getStatus();
    }

    public ModifyOrderQuantityResponseDTO modify_order_quantity(ModifyOrderQuantityRequestDTO dto)
    {
        Order order = order_repository.findById(dto.order_id()).orElseThrow(OrderNotFoundException::new);
        order.setQuantity(dto.quantity());
        order.setPrice(order.getPrice() * dto.quantity());
        order = order_repository.save(order);
        return new ModifyOrderQuantityResponseDTO(order.getId(), order.getQuantity(), order.getPrice());
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
            && SecurityContextHolder.getContext().getAuthentication()
            .getAuthorities().stream()
            .noneMatch(authority ->
                    authority.equals(new SimpleGrantedAuthority("OWNER")) ||
                    authority.equals(new SimpleGrantedAuthority("WAITER"))
            ))
        {
            throw new TableTokenNotMatchException(table.getNumber());
        }
    }
}
