package com.mabis.domain.menu_item;

import com.mabis.domain.dish_type.DishType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "menu_items")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem
{
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private float price;

    @Column
    private String url_image;

    @Column
    private String description;

    @ManyToOne(targetEntity = DishType.class)
    @JoinColumn(name = "dish_type_id")
    private DishType dish_type;
}
