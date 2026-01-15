package com.app.veterinaria.repository;

import com.app.veterinaria.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(String role);

    boolean existsByRole(String role);
}
