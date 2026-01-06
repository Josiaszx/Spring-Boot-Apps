package com.app.veterinaria.repository;

import com.app.veterinaria.dto.VeterinarianDto;
import com.app.veterinaria.entity.Veterinarian;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long> {
    List<Veterinarian> findAllBySpecialty(String specialty);
}
