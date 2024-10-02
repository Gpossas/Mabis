package com.mabis.domain.dish_type;

import com.mabis.domain.menu_item.MenuItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Table(name = "dish_types")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishType
{
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "dish_type", targetEntity = MenuItem.class)
    private List<MenuItem> menu_item;
}
