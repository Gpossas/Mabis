package com.mabis.repositories;

import com.mabis.domain.menu_item.MenuItem;
import com.mabis.domain.menu_item.ResponseMenuItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, UUID>
{
    @Query("""
            SELECT new com.mabis.domain.menu_item.ResponseMenuItemDTO(
                m.id, m.name, m.price, m.description, a.url)
            FROM MenuItem m
            LEFT JOIN m.attachment a
    """)
    List<ResponseMenuItemDTO> find_all();
}
