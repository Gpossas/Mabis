package com.mabis.domain.category;

import com.mabis.domain.menu_item.MenuItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Table(name = "categories")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category
{
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<MenuItem> menu_items;
}
