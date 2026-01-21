package com.app.veterinaria.repository;

import com.app.veterinaria.entity.Owner;
import com.app.veterinaria.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    boolean existsByPhoneNumber(String phoneNumber);

    Optional<Owner> findByUser(User user);
}
