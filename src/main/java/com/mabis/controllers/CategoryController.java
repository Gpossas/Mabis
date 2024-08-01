package com.mabis.controllers;

import com.mabis.domain.category.Category;
import com.mabis.domain.category.CreateCategoryDTO;
import com.mabis.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
