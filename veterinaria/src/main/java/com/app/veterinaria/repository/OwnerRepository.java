package com.app.veterinaria.repository;

import com.app.veterinaria.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
}
