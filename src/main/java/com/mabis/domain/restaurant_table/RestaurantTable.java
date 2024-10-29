package com.mabis.domain.restaurant_table;


import com.mabis.domain.attachment.Attachment;
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

    @Column(nullable = false)
    private String status = table_status.INACTIVE.getStatus();

    @OneToOne(targetEntity = Attachment.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "qr_code_id")
    private Attachment qr_code;

    public RestaurantTable(int number, int capacity)
    {
        this.number = number;
        this.capacity = capacity;
    }
}
