package com.mabis.controllers;

import com.mabis.domain.dish_type.CreateDishTypeDTO;
import com.mabis.domain.dish_type.ResponseDishTypeDTO;
import com.mabis.services.DishTypeService;
import com.mabis.services.JWTService;
import com.mabis.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;


import java.util.UUID;


@WebMvcTest(DishTypeController.class)
class DishTypeControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockBean
    private DishTypeService dish_type_service;

    @MockBean
    private JWTService jwtService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @WithMockUser(authorities = "OWNER")
    @Test
    void create_dish_type() throws Exception
    {
        Mockito.when(dish_type_service.create_dish_type(Mockito.any()))
            .thenReturn(new ResponseDishTypeDTO(UUID.randomUUID(), "Starter"));

        String payload = new ObjectMapper().writeValueAsString(new CreateDishTypeDTO("starter"));

        mvc.perform(
            MockMvcRequestBuilders.post("/dish-types/create")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(payload)
            )
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @WithMockUser(authorities = "OWNER")
    @Test
    void throw_400_missing_dto_parameter_payload() throws Exception
    {
        mvc.perform(
            MockMvcRequestBuilders.post("/dish-types/create")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @WithMockUser(authorities = "OWNER")
    @Test
    void throw_400_invalid_dto_parameters() throws Exception
    {
        String payload = new ObjectMapper().writeValueAsString(
                new CreateDishTypeDTO("name_with_more_than_30_characters_long_is_not_allowed"));

        mvc.perform(
            MockMvcRequestBuilders.post("/dish-types/create")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(payload)
        )
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("name"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message").value("size must be between 3 and 30"));
    }
}