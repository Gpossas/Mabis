package com.mabis.repositories;

import com.mabis.domain.category.Category;
import com.mabis.domain.category.ResponseCategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID>
{
    @Query("""
            SELECT new com.mabis.domain.category.ResponseCategoryDTO(c.id, c.name)
            FROM Category c
            ORDER BY c.name
    """)
    List<ResponseCategoryDTO> findAllCategoriesSorted();
}
