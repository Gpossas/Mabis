package com.mabis.repositories;

import com.mabis.domain.restaurant_table.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TableRepository extends JpaRepository<RestaurantTable, UUID>
{

    @Query("SELECT MAX(t.number) FROM RestaurantTable t")
    Optional<Integer> get_max_table_number();
}
