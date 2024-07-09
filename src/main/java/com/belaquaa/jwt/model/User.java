package com.belaquaa.jwt.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor(force = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull(message = "Age is mandatory")
    @Min(value = 0, message = "Age must be greater than 0")
    @Column(name = "age", nullable = false)
    private Integer age;

    @NotBlank(message = "Email is mandatory")
    @Size(max = 120, message = "Email must be less than 120 characters")
    @Email(message = "Email should be valid")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Size(min = 3, message = "Password must be at least 3 characters")
    @Column(name = "password", nullable = true)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User(String username, Integer age, String email, String password) {
        this.username = username;
        this.age = age;
        this.email = email;
        this.password = password;
    }
}
