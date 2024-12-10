package com.personalExpenseTracker.personalExpenseTracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    @NotNull
    private int id;

    @Column(name = "username")
    @NotNull
    @Size(min=1,message = "is required")
    private String username;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "enabled")
    @NotNull
    private boolean enabled;

    @Column(name = "first_name")
    @NotNull
    @Size(min=1,message = "is required")
    private String firstname;

    @Column(name = "last_name")
    @NotNull
    @Size(min=1,message = "is required")
    private String lastname;

    @Column(name = "email")
    private String email;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "users_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Collection<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Expense> expenses;
}
