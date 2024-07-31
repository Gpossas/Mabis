package com.mabis.repositories;

import com.mabis.domain.dish_type.DishType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DishTypeRepository extends JpaRepository<DishType, UUID> {}
