package com.app.veterinaria.repository;

import com.app.veterinaria.entity.Owner;
import com.app.veterinaria.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByOwner(Owner owner);
}
