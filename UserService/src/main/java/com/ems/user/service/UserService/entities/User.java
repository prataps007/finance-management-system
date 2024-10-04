package com.ems.user.service.UserService.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name="id")
    private String userId;

    private String name;

    private String email;

    private String password;

    private String role; // ROLE_USER or ROLE_ADMIN

    @Transient
    private List<Budget> budgets = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> authorities; // For role-based access
}
