package com.mabis.controllers;

import com.mabis.domain.category.Category;
import com.mabis.domain.category.CreateCategoryDTO;
import com.mabis.services.CategoryService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Category> create_category( @RequestBody CreateCategoryDTO category_dto)
    {
        return ResponseEntity.ok(category_service.create_category(category_dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> get_all_categories()
    {
        return ResponseEntity.ok(category_service.get_all());
    }
}
