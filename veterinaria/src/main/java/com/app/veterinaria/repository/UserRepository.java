package com.app.veterinaria.repository;

import com.app.veterinaria.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
