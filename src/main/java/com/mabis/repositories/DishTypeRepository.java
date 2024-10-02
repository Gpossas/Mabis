package com.mabis.repositories;

import com.mabis.domain.dish_type.DishType;
import com.mabis.domain.dish_type.ResponseDishTypeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DishTypeRepository extends JpaRepository<DishType, UUID>
{
    @Query("""
        SELECT new com.mabis.domain.dish_type.ResponseDishTypeDTO(d.id, d.name)
        FROM DishType d
        ORDER BY d.name
    """)
    List<ResponseDishTypeDTO> findAllDishTypesSorted();
}
