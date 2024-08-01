package com.mabis.services;

import com.mabis.domain.category.Category;
import com.mabis.domain.category.CreateCategoryDTO;
import com.mabis.domain.category.ResponseCategoryDTO;
import com.mabis.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService
{
    private final CategoryRepository category_repository;

    public ResponseCategoryDTO create_category(CreateCategoryDTO category_dto)
    {
        Category category = new Category();
        category.setName(category_dto.name());
        category = category_repository.save(category);
        return new ResponseCategoryDTO(category.getId(), category.getName());
    }

    public List<ResponseCategoryDTO> get_all()
    {
        return category_repository.findAllCategoriesSorted();
    }
}
