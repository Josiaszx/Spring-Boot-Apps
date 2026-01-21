package com.app.veterinaria.entity;

import com.app.veterinaria.dto.AnyEntityRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false, length = 60)
    private String email;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false, updatable = false)
    private LocalDate createdAt;

    public User(AnyEntityRequest newEntityRequest, Role role) {
        this.username = newEntityRequest.getUsername();
        this.password = newEntityRequest.getPassword();
        this.email = newEntityRequest.getEmail();
        this.active = newEntityRequest.getActive() == null || newEntityRequest.getActive();
        this.createdAt = newEntityRequest.getCreatedAt() == null ? LocalDate.now() : newEntityRequest.getCreatedAt();
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", active=" + active +
                ", createdAt=" + createdAt +
                '}';
    }
}
