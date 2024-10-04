package com.mabis.domain.restaurant_table;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "tables")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantTable
{
    @Getter
    @AllArgsConstructor
    public enum table_status
    {
        ACTIVE("active"),
        INACTIVE("inactive"),
        RESERVED("reserved");
        private final String status;
    }

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, name = "table_number")
    private int number;

    @Column(nullable = false)
    private int capacity;

    @Column
    private String qr_code_url;

    @Column(nullable = false)
    private String status = table_status.INACTIVE.getStatus();

    public RestaurantTable(int number, int quantity)
    {
        this.number = number;
        this.capacity = quantity;
    }
}
