package com.mabis.services;

import com.mabis.exceptions.NotActiveTableException;
import com.mabis.exceptions.TableNotFoundException;
import com.mabis.exceptions.TableTokenNotMatchException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest
{
    @Test
    void test_place_order_non_existent_table_throw_exception()
    {
        assertThatThrownBy(() -> order_service.place_order())
                .isInstanceOf(TableNotFoundException.class)
                .hasMessage("Table not found");
    }

    @Test
    void test_place_order_not_active_table_throw_exception()
    {
        assertThatThrownBy(() -> order_service.place_order())
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
        assertThatThrownBy(() -> order_service.delete_order())
                .isInstanceOf(TableTokenNotMatchException.class)
                .hasMessage("You're not authorized to place orders in table " + table_number);
    }

    @Test
    void test_successful_place_order(){}

    @Test
    void test_successful_delete_order(){}
}