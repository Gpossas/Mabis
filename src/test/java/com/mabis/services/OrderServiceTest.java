package com.mabis.services;

import com.mabis.domain.attachment.Attachment;
import com.mabis.domain.menu_item.MenuItem;
import com.mabis.domain.order.*;
import com.mabis.domain.restaurant_table.RestaurantTable;
import com.mabis.domain.user.User;
import com.mabis.domain.user.UserDetailsImpl;
import com.mabis.exceptions.NotActiveTableException;
import com.mabis.exceptions.OrderNotFoundException;
import com.mabis.exceptions.TableNotFoundException;
import com.mabis.exceptions.TableTokenNotMatchException;
import com.mabis.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest
{
    @Mock
    OrderRepository order_repository;

    @Mock
    MenuItemService menu_item_service;

    @Mock
    TableService table_service;

    @InjectMocks
    OrderService order_service;

    @Captor
    ArgumentCaptor<List<Order>> orders_captor;

    @Test
    void test_place_order_non_existent_table_throw_exception()
    {
        OrderRequestDTO dto = new OrderRequestDTO(UUID.randomUUID(), "", null);
        Mockito.when(table_service.get_table_by_id(dto.table_id())).thenThrow(new TableNotFoundException());

        assertThatThrownBy(() -> order_service.place_order(dto))
                .isInstanceOf(TableNotFoundException.class)
                .hasMessage("Table not found");
    }

    @Test
    void test_place_order_not_active_table_throw_exception()
    {
        RestaurantTable table = new RestaurantTable();
        table.setStatus("INACTIVE");
        OrderRequestDTO dto = new OrderRequestDTO(UUID.randomUUID(), null, null);

        Mockito.when(table_service.get_table_by_id(dto.table_id())).thenReturn(table);

        assertThatThrownBy(() -> order_service.place_order(dto))
                .isInstanceOf(NotActiveTableException.class)
                .hasMessage("Table is not active");

        table.setStatus("RESERVED");
        assertThatThrownBy(() -> order_service.place_order(dto))
                .isInstanceOf(NotActiveTableException.class)
                .hasMessage("Table is not active");
    }

    @Test
    void test_delete_non_existent_order_throw_exception()
    {
        assertThatThrownBy(() -> order_service.delete_order(UUID.randomUUID()))
                .isInstanceOf(OrderNotFoundException.class)
                .hasMessage("Order not found");
    }

    @Test
    void test_credentials_not_match_to_place_order_throw_exception()
    {
        RestaurantTable table = new RestaurantTable();
        table.setNumber(98);
        table.setStatus("active");
        table.setQr_code(new Attachment("token", "urll"));
        OrderRequestDTO dto = new OrderRequestDTO(UUID.randomUUID(), "wrong-token", null);
        authenticate_user_with_role(User.Roles.COOK); // this user role is not allowed to place orders

        Mockito.when(table_service.get_table_by_id(dto.table_id())).thenReturn(table);

        assertThatThrownBy(() -> order_service.place_order(dto))
                .isInstanceOf(TableTokenNotMatchException.class)
                .hasMessage("You're not authorized to place orders in table " + 98);
    }

    @Test
    void test_users_with_allowed_roles_can_access_table_without_passing_table_token()
    {
        RestaurantTable table = new RestaurantTable();
        table.setNumber(98);
        table.setStatus("active");
        table.setQr_code(new Attachment("token", "urll"));
        OrderRequestDTO dto = new OrderRequestDTO(UUID.randomUUID(), "wrong-token", new ArrayList<>());

        Mockito.when(table_service.get_table_by_id(dto.table_id())).thenReturn(table);
        Mockito.when(menu_item_service.find_id_in(Mockito.any())).thenReturn(new HashSet<>());

        authenticate_user_with_role(User.Roles.OWNER);
        assertDoesNotThrow(() -> order_service.place_order(dto));
        authenticate_user_with_role(User.Roles.WAITER);
        assertDoesNotThrow(() -> order_service.place_order(dto));
    }

    @Test
    void test_successful_place_order()
    {
        RestaurantTable table = new RestaurantTable();
        table.setNumber(98);
        table.setStatus("active");
        table.setQr_code(new Attachment("token", "url"));
        MenuItem menu_item1 = new MenuItem();
        MenuItem menu_item2 = new MenuItem();
        menu_item1.setId(UUID.randomUUID());
        menu_item2.setId(UUID.randomUUID());
        OrderRequestDTO dto = new OrderRequestDTO(UUID.randomUUID(), "token", new ArrayList<>(List.of(
                new OrderItemDTO(menu_item1.getId(),1, ""),
                new OrderItemDTO(menu_item2.getId(),1, ""),
                new OrderItemDTO(UUID.randomUUID(),1, ""))
        ));
        HashSet<MenuItem> menu_items_found = new HashSet<>();
        menu_items_found.add(menu_item1);
        menu_items_found.add(menu_item2);

        Mockito.when(table_service.get_table_by_id(dto.table_id())).thenReturn(table);
        Mockito.when(menu_item_service.find_id_in(Mockito.any())).thenReturn(menu_items_found);

        order_service.place_order(dto);

        Mockito.verify(order_repository).saveAll(orders_captor.capture());
        assertEquals(2, orders_captor.getValue().size());
    }

    @Test
    void test_successful_delete_order()
    {
        UUID id = UUID.randomUUID();

        Mockito.when(order_repository.findById(id)).thenReturn(Optional.ofNullable(Mockito.mock(Order.class)));

        order_service.delete_order(id);
        Mockito.verify(order_repository).deleteById(id);
    }

    @Test
    void test_thow_exception_update_status_order_in_non_existent_order()
    {
        assertThatThrownBy(() -> order_service.update_status_order(Mockito.mock(UpdateStatusOrderRequestDTO.class)))
                .isInstanceOf(OrderNotFoundException.class)
                .hasMessage("Order not found");
    }

    @Test
    void test_successful_update_status_order()
    {
        UpdateStatusOrderRequestDTO dto = new UpdateStatusOrderRequestDTO(UUID.randomUUID(),
                Order.OrderStatus.PREPARING.getStatus());
        Order order_mock = Mockito.mock(Order.class);

        Mockito.when(order_repository.findById(dto.order_id())).thenReturn(Optional.ofNullable(order_mock));

        order_service.update_status_order(dto);
        Mockito.verify(order_mock).setStatus(Order.OrderStatus.PREPARING);
        Mockito.verify(order_mock).setStatus_updated_at(Mockito.any(LocalDateTime.class));
    }

    @Test
    void test_throw_exception_order_not_found_when_modifying_quantity_non_existent_order()
    {
        assertThatThrownBy(() -> order_service.update_status_order(Mockito.mock(UpdateStatusOrderRequestDTO.class)))
                .isInstanceOf(OrderNotFoundException.class)
                .hasMessage("Order not found");
    }

    @Test
    void test_successful_modify_order_quantity()
    {
        ModifyOrderQuantityRequestDTO dto = new ModifyOrderQuantityRequestDTO(UUID.randomUUID(), 3);
        Order order_mock = Mockito.spy(new Order());
        Float price = 8.32F;
        order_mock.setPrice(price);
        order_mock.setId(dto.order_id());

        Mockito.when(order_repository.findById(dto.order_id())).thenReturn(Optional.of(order_mock));
        Mockito.when(order_repository.save(order_mock)).thenReturn(order_mock);

        order_service.modify_order_quantity(dto);
        Mockito.verify(order_mock).setQuantity(dto.quantity());
        Mockito.verify(order_mock).setPrice(price * dto.quantity());
    }

    private void authenticate_user_with_role(User.Roles role)
    {
        User user = new User();
        user.setRole(role);
        UserDetailsImpl user_details = new UserDetailsImpl(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user_details, null, user_details.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}