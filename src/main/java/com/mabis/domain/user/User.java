package com.mabis.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "users")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User
{
    @Getter
    @AllArgsConstructor
    enum Roles
    {
        OWNER("OWNER"),
        WAITER("WAITER"),
        COOK("COOK");
        private final String role;
    }

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String first_name;

    private String last_name;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Roles role;

    public User(RegisterUserDTO dto)
    {
        this.first_name = dto.first_name();
        this.last_name = dto.last_name();
        this.email = dto.email().toLowerCase();
        this.role = Roles.valueOf(dto.role());
    }
}
