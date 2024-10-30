package com.mabis.domain.attachment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "attachments")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attachment
{
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String url;

    public Attachment(String name, String url)
    {
        this.name = name;
        this.url = url;
    }
}
