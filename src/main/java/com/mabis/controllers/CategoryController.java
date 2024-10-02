package com.mabis.controllers;

import com.mabis.domain.category.CreateCategoryDTO;
import com.mabis.domain.category.ResponseCategoryDTO;
import com.mabis.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController
{
    private final CategoryService category_service;

    @PostMapping("/create")
    public ResponseEntity<ResponseCategoryDTO> create_category(@Valid @RequestBody CreateCategoryDTO category_dto)
    {
        return new ResponseEntity<>(category_service.create_category(category_dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ResponseCategoryDTO>> get_all_categories()
    {
        return ResponseEntity.ok(category_service.get_all());
    }
}
