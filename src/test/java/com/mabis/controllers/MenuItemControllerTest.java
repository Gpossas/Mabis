package com.mabis.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mabis.domain.menu_item.CreateMenuItemDTO;
import com.mabis.infra.MultipleErrorsResponse;
import com.mabis.services.MenuItemService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;


@WebMvcTest(MenuItemController.class)
class MenuItemControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockBean
    private MenuItemService menu_item_service;

    @Test
    void test_missing_fields_dto_creating_menu_item() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/menu-items/create")
                .contentType(MediaType.MULTIPART_FORM_DATA)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest())
         .andExpect(MockMvcResultMatchers.jsonPath("$.http_status").value("BAD_REQUEST"))
         .andExpect(MockMvcResultMatchers.jsonPath("$.errors[?(@.message == 'must not be null')]").exists())
         .andExpect(MockMvcResultMatchers.jsonPath("$.errors[?(@.message == 'must not be blank')]").exists())
         .andExpect(MockMvcResultMatchers.jsonPath("$.errors[?(@.field == 'price')]").exists())
         .andExpect(MockMvcResultMatchers.jsonPath("$.errors[?(@.field == 'name')]").exists())
         .andExpect(MockMvcResultMatchers.jsonPath("$.errors[?(@.field == 'dish_type_id')]").exists())
         .andExpect(MockMvcResultMatchers.jsonPath("$.errors.length()").value(3));
    }
}