package com.mabis.services;

import com.mabis.domain.attachment.Attachment;
import com.mabis.domain.order.OrderRequestDTO;
import com.mabis.domain.restaurant_table.RestaurantTable;
import com.mabis.domain.user.User;
import com.mabis.domain.user.UserDetailsImpl;
import com.mabis.exceptions.NotActiveTableException;
import com.mabis.exceptions.TableNotFoundException;
import com.mabis.exceptions.TableTokenNotMatchException;
import com.mabis.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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
        assertThatThrownBy(() -> order_service.delete_order())
                .isInstanceOf(NotActiveTableException.class)
                .hasMessage("Table is not active");
    }

    @Test
    void test_credentials_not_match_to_place_order_throw_exception()
    {
        RestaurantTable table = new RestaurantTable();
        table.setNumber(98);
        table.setStatus("active");
        table.setQr_code(new Attachment("token", "urll"));
        OrderRequestDTO dto = new OrderRequestDTO(UUID.randomUUID(), "wrong-token", null);

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
    void test_successful_place_order(){}

    @Test
    void test_successful_delete_order(){}

    private void authenticate_user_with_role(User.Roles role)
    {
        User user = new User();
        user.setRole(role);
        UserDetailsImpl user_details = new UserDetailsImpl(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user_details, null, user_details.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}