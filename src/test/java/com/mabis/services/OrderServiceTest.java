package com.mabis.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest
{
    @Test
    void test_place_order_non_existent_table_throw_exception(){}

    @Test
    void test_place_order_non_existent_menu_item_throw_exception(){}

    @Test
    void test_place_order_not_active_table_throw_exception(){}

    @Test
    void test_delete_non_existent_order_throw_exception(){}

    @Test
    void test_successful_place_order(){}

    @Test
    void test_successful_delete_order(){}
}