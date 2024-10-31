package com.mabis.domain.order;

import com.mabis.domain.menu_item.MenuItem;
import com.mabis.domain.restaurant_table.RestaurantTable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order
{
    @Getter
    @AllArgsConstructor
    public enum OrderStatus
    {
        COMPLETED("COMPLETED"),
        CANCELLED("CANCELLED"),
        PREPARING("PREPARING"),
        PENDING("PENDING");

        private final String status;
    }

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Float price;

    @Column
    private String description;

    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(nullable = false)
    private LocalDateTime status_updated_at;

    @Column(nullable = false)
    private LocalDateTime order_placed_at = LocalDateTime.now();

    @ManyToOne(targetEntity = RestaurantTable.class)
    @JoinColumn(name = "table_id")
    private RestaurantTable table;

    @ManyToOne(targetEntity = MenuItem.class)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menu_item;
}
