package com.mabis.domain.menu_item;

import com.mabis.domain.attachment.Attachment;
import com.mabis.domain.category.Category;
import com.mabis.domain.dish_type.DishType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
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
    private String description;

    @OneToOne(targetEntity = Attachment.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Attachment attachment;

    @ManyToOne(targetEntity = DishType.class)
    @JoinColumn(name = "dish_type_id")
    private DishType dish_type;

    @ManyToMany(targetEntity = Category.class)
    @JoinTable(
            name = "menu_items_categories",
            joinColumns = { @JoinColumn(name = "menu_item_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") }
    )
    private Set<Category> categories;

    public MenuItem(CreateMenuItemDTO menuItemDto)
    {
        this.name = this.capitalize(menuItemDto.name());
        this.price = menuItemDto.price();
        this.description = menuItemDto.description() != null ? this.capitalize(menuItemDto.description()) : null;
    }

    private String capitalize(String string)
    {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}
