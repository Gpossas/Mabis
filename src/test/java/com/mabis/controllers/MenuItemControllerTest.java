package com.mabis.controllers;

import com.mabis.services.JWTService;
import com.mabis.services.MenuItemService;
import com.mabis.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@WebMvcTest(MenuItemController.class)
class MenuItemControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockBean
    private JWTService jwtService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private MenuItemService menu_item_service;

    @WithMockUser(authorities = "OWNER")
    @Test
    void test_missing_fields_dto_creating_menu_item() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/menu-items/create")
                .with(csrf())
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