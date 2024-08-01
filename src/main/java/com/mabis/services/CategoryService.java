package com.mabis.services;

import com.mabis.domain.category.Category;
import com.mabis.domain.category.CreateCategoryDTO;
import com.mabis.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService
{
    private final CategoryRepository category_repository;

    public Category create_category(CreateCategoryDTO category_dto)
    {
        Category category = new Category();
        category.setName(category_dto.name());
        return category_repository.save(category); //TODO: don't show many-to-many field in json
    }

    public List<Category> get_all()
    {
        return category_repository.findAll();
    }
}
